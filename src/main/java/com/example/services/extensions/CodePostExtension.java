package com.example.services.extensions;

@PostExtensionIdentifier("awesome_code")
public class CodePostExtension implements PostExtension {
    @Override
    public Object getData(String stub) {
        return null;
    }
}
