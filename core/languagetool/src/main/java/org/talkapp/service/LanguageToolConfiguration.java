package org.talkapp.service;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author Budnikau Aliaksandr
 */
@Configuration
public class LanguageToolConfiguration {
    @Bean(destroyMethod = "close")
    public AmericanEnglish americanEnglish() {
        return new AmericanEnglish();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public JLanguageTool languageTool(AmericanEnglish americanEnglish) {
        return new JLanguageTool(americanEnglish);
    }
}