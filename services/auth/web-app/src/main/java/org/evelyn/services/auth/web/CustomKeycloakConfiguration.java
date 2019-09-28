package org.evelyn.services.auth.web;

//import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class CustomKeycloakConfiguration {
//    /**
//     * Configures OAuth Login with Spring Security 5.
//     * @return
//     */
//    @Bean
//    public WebSecurityConfigurerAdapter webSecurityConfigurer(@Value("${keycloak-client.realm}") final String realm) {
//        return new WebSecurityConfigurerAdapter() {
//            @Override
//            public void configure(HttpSecurity http) throws Exception {
//                http
//                        // Configure session management to your needs.
//                        // I need this as a basis for a classic, server side rendered application
//                        .sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .and()
//                        // Depends on your taste. You can configure single paths here
//                        // or allow everything a I did and then use method based security
//                        // like in the controller below
//                        .authorizeRequests()
//                        .anyRequest().permitAll()
//                        .and()
//                        // This is the point where OAuth2 login of Spring 5 gets enabled
//                        .oauth2Login()
//                        // I don't want a page with different clients as login options
//                        // So i use the constant from OAuth2AuthorizationRequestRedirectFilter
//                        // plus the configured realm as immediate redirect to Keycloak
//                        .loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + realm);
//            }
//        };
//    }
//}
