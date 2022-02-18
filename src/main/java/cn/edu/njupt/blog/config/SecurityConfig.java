package cn.edu.njupt.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.PublicKey;

/**
 * 安全配置类
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "njupt.edu.cn";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //使用BCrypt加密
    }


    /**
     * 自定义配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()  //都可以访问
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/admins/**").hasRole("ADMIN") //需要相应角色才能访问
                .and()
                .formLogin() //基于Form表单登录验证
                .loginPage("/login").failureUrl("/login-error") //自定义登陆界面
                .and().rememberMe().key(KEY) //启用remember me
                .and().exceptionHandling().accessDeniedHandler("/403"); //处理异常，拒绝访问重定向到403页面
        http.csrf().ignoringAntMatchers("/h2-console/**"); //禁用H2控制台的CSRF防护
        http.headers().frameOptions().sameOrigin(); //允许来自同一来源的H2控制台的请求
    }

    /**
     * 认证信息管理
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); //设置密码加密方式
        return authenticationProvider;
    }
}
