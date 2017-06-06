package org.talkapp.service.languagetool;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author Budnikau Aliaksandr
 */
@Configuration
public class LanguageToolConfiguration {
    @Bean(destroyMethod = "close")
    public AmericanEnglish americanEnglish() {
        return new AmericanEnglish();
    }

    //@RequestScope
    @Bean(destroyMethod = "close")
    public JLanguageTool americanEnglish(AmericanEnglish americanEnglish) {
        return new JLanguageTool(americanEnglish);
    }
}
