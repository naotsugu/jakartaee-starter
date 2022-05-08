# JPA static metamodel enhancer

## Usage

```kotlin
dependencies {
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.0.0.Final")
    annotationProcessor(project(":modelgen-enhancer", "archives"))
}
```


To build a specification, one typically writes as follows :

```java
public static Specification<Employee> deptNameLike(final String name) {
    return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
        cb.equal(root.get(Employee_.department).get(Department_.address).get(Address_city), name);
}
```


When use the enhanced metamodel, It can be written as follows.

```java
public static Specification<Employee> deptNameLike(final String name) {
    return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
        cb.equal(on(root).getDepartment().getAddress().getCity(), name);
}

private static Employee_Root on(Root<Employee> root) {
    return new Employee_Root(root);
}
```
