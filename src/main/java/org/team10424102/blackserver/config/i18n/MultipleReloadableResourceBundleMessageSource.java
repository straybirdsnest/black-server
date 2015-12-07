package org.team10424102.blackserver.config.i18n;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sk on 15-12-6.
 */
@Configuration
public class MultipleReloadableResourceBundleMessageSource extends AbstractMessageSource {

  private static final String CLASSPATH_PREFIX = "classpath*:";

  private static final String PROPERTIES_SUFFIX = ".properties";

  private static final String XML_SUFFIX = ".xml";

  private String[] basenames = new String[0];

  private String defaultEncoding;

  private Properties fileEncodings;

  private boolean fallbackToSystemLocale = true;

  private long cacheMillis = -1;

  private boolean concurrentRefresh = true;

  private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

  private PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

  private final ConcurrentMap<String, Map<Locale, List<String>>> cachedFilenames =
      new ConcurrentHashMap<String, Map<Locale, List<String>>>();

  private final ConcurrentMap<String, PropertiesHolder> cachedProperties =
      new ConcurrentHashMap<String, PropertiesHolder>();

  private final ConcurrentMap<Locale, PropertiesHolder> cachedMergedProperties =
      new ConcurrentHashMap<Locale, PropertiesHolder>();

  public void setBasename(String basename) {
    setBasenames(basename);
  }

  public void setBasenames(String... basenames) {
    if (basenames != null) {
      this.basenames = new String[basenames.length];
      for (int i = 0; i < basenames.length; i++) {
        String basename = basenames[i];
        Assert.hasText(basename, "Basename must not be empty");
        this.basenames[i] = basename.trim();
      }
    } else {
      this.basenames = new String[0];
    }
  }

  public void setDefaultEncoding(String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  public void setFileEncodings(Properties fileEncodings) {
    this.fileEncodings = fileEncodings;
  }

  public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
    this.fallbackToSystemLocale = fallbackToSystemLocale;
  }

  public void setCacheSeconds(int cacheSeconds) {
    this.cacheMillis = cacheSeconds * 1000L;
  }

  public void setConcurrentRefresh(boolean concurrentRefresh) {
    this.concurrentRefresh = concurrentRefresh;
  }

  public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
    this.propertiesPersister =
        propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister();
  }

  @Override
  protected String resolveCodeWithoutArguments(String code, Locale locale) {
    if (this.cacheMillis < 0) {
      PropertiesHolder propHolder = getMergedProperties(locale);
      String result = propHolder.getProperty(code);
      if (result != null) {
        return result;
      }
    } else {
      for (String basename : this.basenames) {
        List<String> filenames = calculateAllFilenames(basename, locale);
        for (String filename : filenames) {
          PropertiesHolder propHolder = getProperties(filename);
          String result = propHolder.getProperty(code);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }

  @Override
  protected MessageFormat resolveCode(String code, Locale locale) {
    if (this.cacheMillis < 0) {
      PropertiesHolder propHolder = getMergedProperties(locale);
      MessageFormat result = propHolder.getMessageFormat(code, locale);
      if (result != null) {
        return result;
      }
    } else {
      for (String basename : this.basenames) {
        List<String> filenames = calculateAllFilenames(basename, locale);
        for (String filename : filenames) {
          PropertiesHolder propHolder = getProperties(filename);
          MessageFormat result = propHolder.getMessageFormat(code, locale);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }

  protected PropertiesHolder getMergedProperties(Locale locale) {
    PropertiesHolder mergedHolder = this.cachedMergedProperties.get(locale);
    if (mergedHolder != null) {
      return mergedHolder;
    }
    Properties mergedProps = new Properties();
    mergedHolder = new PropertiesHolder(mergedProps, -1);
    for (int i = this.basenames.length - 1; i >= 0; i--) {
      List<String> filenames = calculateAllFilenames(this.basenames[i], locale);
      for (int j = filenames.size() - 1; j >= 0; j--) {
        String filename = filenames.get(j);
        PropertiesHolder propHolder = getProperties(filename);
        if (propHolder.getProperties() != null) {
          mergedProps.putAll(propHolder.getProperties());
        }
      }
    }
    PropertiesHolder existing = this.cachedMergedProperties.putIfAbsent(locale, mergedHolder);
    if (existing != null) {
      mergedHolder = existing;
    }
    return mergedHolder;
  }

  protected List<String> calculateAllFilenames(String basename, Locale locale) {
    Map<Locale, List<String>> localeMap = this.cachedFilenames.get(basename);
    if (localeMap != null) {
      List<String> filenames = localeMap.get(locale);
      if (filenames != null) {
        return filenames;
      }
    }
    List<String> filenames = new ArrayList<String>(7);
    filenames.addAll(calculateFilenamesForLocale(basename, locale));
    if (this.fallbackToSystemLocale && !locale.equals(Locale.getDefault())) {
      List<String> fallbackFilenames = calculateFilenamesForLocale(basename, Locale.getDefault());
      for (String fallbackFilename : fallbackFilenames) {
        if (!filenames.contains(fallbackFilename)) {
          // Entry for fallback locale that isn't already in filenames list.
          filenames.add(fallbackFilename);
        }
      }
    }
    filenames.add(basename);
    if (localeMap == null) {
      localeMap = new ConcurrentHashMap<Locale, List<String>>();
      Map<Locale, List<String>> existing = this.cachedFilenames.putIfAbsent(basename, localeMap);
      if (existing != null) {
        localeMap = existing;
      }
    }
    localeMap.put(locale, filenames);
    return filenames;
  }

  protected List<String> calculateFilenamesForLocale(String basename, Locale locale) {
    List<String> result = new ArrayList<String>(3);
    String language = locale.getLanguage();
    String country = locale.getCountry();
    String variant = locale.getVariant();
    StringBuilder temp = new StringBuilder(basename);

    temp.append('_');
    if (language.length() > 0) {
      temp.append(language);
      result.add(0, temp.toString());
    }

    temp.append('_');
    if (country.length() > 0) {
      temp.append(country);
      result.add(0, temp.toString());
    }

    if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
      temp.append('_').append(variant);
      result.add(0, temp.toString());
    }

    return result;
  }

  protected PropertiesHolder getProperties(String filename) {
    PropertiesHolder propHolder = this.cachedProperties.get(filename);
    long originalTimestamp = -2;

    if (propHolder != null) {
      originalTimestamp = propHolder.getRefreshTimestamp();
      if (originalTimestamp == -1 || originalTimestamp > System.currentTimeMillis() - this.cacheMillis) {
        // Up to date
        return propHolder;
      }
    } else {
      propHolder = new PropertiesHolder();
      PropertiesHolder existingHolder = this.cachedProperties.putIfAbsent(filename, propHolder);
      if (existingHolder != null) {
        propHolder = existingHolder;
      }
    }

    // At this point, we need to refresh...
    if (this.concurrentRefresh && propHolder.getRefreshTimestamp() >= 0) {
      // A populated but stale holder -> could keep using it.
      if (!propHolder.refreshLock.tryLock()) {
        // Getting refreshed by another thread already ->
        // let's return the existing properties for the time being.
        return propHolder;
      }
    } else {
      propHolder.refreshLock.lock();
    }
    try {
      PropertiesHolder existingHolder = this.cachedProperties.get(filename);
      if (existingHolder != null && existingHolder.getRefreshTimestamp() > originalTimestamp) {
        return existingHolder;
      }
      return refreshProperties(filename, propHolder);
    } finally {
      propHolder.refreshLock.unlock();
    }
  }

  protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
    long refreshTimestamp = this.cacheMillis < 0 ? -1 : System.currentTimeMillis();

    PropertiesHolder newPropHolder = propHolder;
    Resource[] resources = new Resource[0];
    try {
      resources = this.resourceLoader.getResources(CLASSPATH_PREFIX + filename + PROPERTIES_SUFFIX);
      if (resources.length == 0) {
        resources = this.resourceLoader.getResources(CLASSPATH_PREFIX + filename + XML_SUFFIX);
      }
    } catch (IOException e) {
      if (logger.isWarnEnabled()) {
        logger.warn("Could not find properties file [" + filename + "]", e);
      }
    }

    if (resources.length > 0) {
      long fileTimestamp = -1;
      if (this.cacheMillis >= 0) {
        // Last-modified timestamp of file will just be read if caching with timeout.
        try {
          fileTimestamp = resources[0].lastModified();
          if (newPropHolder != null && newPropHolder.getFileTimestamp() == fileTimestamp) {
            if (logger.isDebugEnabled()) {
              logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
            }
            newPropHolder.setRefreshTimestamp(refreshTimestamp);
            return newPropHolder;
          }
        } catch (IOException ex) {
          // Probably a class path resources: cache it forever.
          if (logger.isDebugEnabled()) {
            logger.debug(resources + " could not be resolved in the file system - assuming that it hasn't changed", ex);
          }
          fileTimestamp = -1;
        }
      }
      try {
        Properties props = loadProperties(resources, filename);
        newPropHolder = new PropertiesHolder(props, fileTimestamp);
      } catch (IOException ex) {
        if (logger.isWarnEnabled()) {
          logger.warn("Could not parse properties file [" + filename + "]", ex);
        }
        // Empty holder representing "not valid".
        newPropHolder = new PropertiesHolder();
      }
    } else {
      // Resource does not exist.
      if (logger.isDebugEnabled()) {
        logger.debug("No properties file found for [" + filename + "] - neither plain properties nor XML");
      }
      // Empty holder representing "not found".
      newPropHolder = new PropertiesHolder();
    }

    newPropHolder.setRefreshTimestamp(refreshTimestamp);
    this.cachedProperties.put(filename, newPropHolder);
    return newPropHolder;
  }

  protected Properties loadProperties(Resource[] resources, String filename) throws IOException {
    Properties props = new Properties();
    for (Resource resource : resources) {
      loadProperties(props, resource, filename);
    }
    return props;
  }

  protected void loadProperties(Properties props, Resource resource, String filename) throws IOException {
    InputStream is = resource.getInputStream();
    try {
      if (resource.getFilename().endsWith(XML_SUFFIX)) {
        if (logger.isDebugEnabled()) {
          logger.debug("Loading properties [" + resource.getFilename() + "]");
        }
        this.propertiesPersister.loadFromXml(props, is);
      } else {
        String encoding = null;
        if (this.fileEncodings != null) {
          encoding = this.fileEncodings.getProperty(filename);
        }
        if (encoding == null) {
          encoding = this.defaultEncoding;
        }
        if (encoding != null) {
          if (logger.isDebugEnabled()) {
            logger.debug("Loading properties [" + resource.getFilename() + "] with encoding '" + encoding + "'");
          }
          this.propertiesPersister.load(props, new InputStreamReader(is, encoding));
        } else {
          if (logger.isDebugEnabled()) {
            logger.debug("Loading properties [" + resource.getFilename() + "]");
          }
          this.propertiesPersister.load(props, is);
        }
      }
    } finally {
      is.close();
    }
  }

  public void clearCache() {
    logger.debug("Clearing entire resource bundle cache");
    this.cachedProperties.clear();
    this.cachedMergedProperties.clear();
  }

  public void clearCacheIncludingAncestors() {
    clearCache();
    if (getParentMessageSource() instanceof MultipleReloadableResourceBundleMessageSource) {
      ((MultipleReloadableResourceBundleMessageSource) getParentMessageSource()).clearCacheIncludingAncestors();
    }
  }

  @Override
  public String toString() {
    return getClass().getName() + ": basenames=[" + StringUtils.arrayToCommaDelimitedString(this.basenames) + "]";
  }

  protected class PropertiesHolder {

    private final Properties properties;

    private final long fileTimestamp;

    private volatile long refreshTimestamp = -2;

    private final ReentrantLock refreshLock = new ReentrantLock();

    private final ConcurrentMap<String, Map<Locale, MessageFormat>> cachedMessageFormats =
        new ConcurrentHashMap<String, Map<Locale, MessageFormat>>();

    public PropertiesHolder() {
      this.properties = null;
      this.fileTimestamp = -1;
    }

    public PropertiesHolder(Properties properties, long fileTimestamp) {
      this.properties = properties;
      this.fileTimestamp = fileTimestamp;
    }

    public Properties getProperties() {
      return this.properties;
    }

    public long getFileTimestamp() {
      return this.fileTimestamp;
    }

    public void setRefreshTimestamp(long refreshTimestamp) {
      this.refreshTimestamp = refreshTimestamp;
    }

    public long getRefreshTimestamp() {
      return this.refreshTimestamp;
    }

    public String getProperty(String code) {
      if (this.properties == null) {
        return null;
      }
      return this.properties.getProperty(code);
    }

    public MessageFormat getMessageFormat(String code, Locale locale) {
      if (this.properties == null) {
        return null;
      }
      Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats.get(code);
      if (localeMap != null) {
        MessageFormat result = localeMap.get(locale);
        if (result != null) {
          return result;
        }
      }
      String msg = this.properties.getProperty(code);
      if (msg != null) {
        if (localeMap == null) {
          localeMap = new ConcurrentHashMap<Locale, MessageFormat>();
          Map<Locale, MessageFormat> existing = this.cachedMessageFormats.putIfAbsent(code, localeMap);
          if (existing != null) {
            localeMap = existing;
          }
        }
        MessageFormat result = createMessageFormat(msg, locale);
        localeMap.put(locale, result);
        return result;
      }
      return null;
    }
  }

}
