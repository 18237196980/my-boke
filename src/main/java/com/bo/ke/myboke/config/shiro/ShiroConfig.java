package com.bo.ke.myboke.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //调用配置的权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截配置
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();

        //anon表示此地址不需要任何权限即可访问
        filterChainDefinitions.put("/assets/**", "anon");
        filterChainDefinitions.put("/download/**", "anon");
        filterChainDefinitions.put("/druid/**", "anon");
        filterChainDefinitions.put("/boke/user/login", "anon");

        // 登录 登出
        filterChainDefinitions.put("/sys/captcha", "anon");
        filterChainDefinitions.put("/sys/randomImage/**", "anon");
        filterChainDefinitions.put("/sys/login", "anon");

        filterChainDefinitions.put("/", "anon");

        // 静态资源
        filterChainDefinitions.put("/doc.html", "anon");
        filterChainDefinitions.put("/**/*.js", "anon");
        filterChainDefinitions.put("/**/*.css", "anon");
        filterChainDefinitions.put("/**/*.html", "anon");
        filterChainDefinitions.put("/**/*.svg", "anon");
        filterChainDefinitions.put("/**/*.pdf", "anon");
        filterChainDefinitions.put("/**/*.jpg", "anon");
        filterChainDefinitions.put("/**/*.png", "anon");
        filterChainDefinitions.put("/**/*.ico", "anon");


        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new AuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitions.put("/**", "jwt");

        // 未授权界面返回JSON
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
        shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);

        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setCacheManager(cacheManager());

        return shiroRealm;
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 支持Shiro对Controller的方法级AOP安全控制
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * shiro里实现的Advisor类,用来拦截注解的方法
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

}
