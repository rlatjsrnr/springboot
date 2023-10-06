## 자동 구성(Auto Configuration)

DbConfig 에서 DataSource , TransactionManager , JdbcTemplate 을 스프링 빈으로 직접 등록 함. <br/>

DbConfigTest 확인해 보면 DataSource , TransactionManager , JdbcTemplate 잘 등록 되어있음. <br/>

그런데 DbConfig의 @Configuration을 제거해 위의 것들을 빈으로 등록하지 않아도 <br/>

DbConfigTest 에서 테스트 해 보면 정상적으로 등록되어 있음을 확인할 수 있음. <br/>
이것이 스프링부트의 자동 구성(Auto Configuration) 기능임.<br/>
일반적으로 자주 사용하는 수 많은 빈들을 자동으로 등록 해주는 기능.

 - JdbcTemplateAutoConfiguration : JdbcTemplate
 - DataSourceAutoConfiguration : DataSource
 - DataSourceTransactionManagerAutoConfiguration : TransactionManager

## @Contional
 - 특정 조건에 맞을 때 설정이 동작하도록 함.
 - 예를 들어서 개발 서버에서 확인 용도로만 해당 기능을 사용하고, 운영 서버에서는 해당 기능을 사용하지 않는 것
 - 핵심은 소스코드를 고치지 않고 이런 것이 가능해야 한다는 점

MemoryConfig에 @Conditional(MemoryCondition.class)를 추가하여 <br/>
MemoryCondition이 정한 memory=on 이라는 환경정보가 존재할 때만 MemoryConfig 설정을 동작하도록 함.

#### 다양한 기능
@ConditionalOnProperty(name = "memory", havingValue = "on") 어노테이션 사용시 위와 같은 동작을 함. <br/>

<strong>@ConditionalOnXxx</strong>
 - @ConditionalOnClass , @ConditionalOnMissingClass
    - 클래스가 있는 경우 동작한다. 나머지는 그 반대
- @ConditionalOnBean , @ConditionalOnMissingBean
    - 빈이 등록되어 있는 경우 동작한다. 나머지는 그 반대
 - @ConditionalOnProperty
    - 환경 정보가 있는 경우 동작한다.
 - @ConditionalOnResource
    - 리소스가 있는 경우 동작한다.
 - @ConditionalOnWebApplication , @ConditionalOnNotWebApplication
    - 웹 애플리케이션인 경우 동작한다.
 - @ConditionalOnExpression
    - SpEL 표현식에 만족하는 경우 동작한다


