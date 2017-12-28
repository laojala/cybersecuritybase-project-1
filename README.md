# Cyber Security Base - Course Project I

Web application that contains five different security flaws from the [OWASP top ten list](https://www.owasp.org/index.php/Top_10_2013-Top_10). Project is done for the course https://cybersecuritybase.github.io/ 

It is recommended not to reuse this code, because it contains security issues.


## Getting Started

### Users

Application has two users:

```
sam/salasana
admin/password
```

After login user can sign in to a event and logout. Admin user can also see list of all signees.

###  Running

Prerequisite is that Maven is installed. Application can be run using:
```
mvn spring-boot:run
```



## Security flaws and suggested fixes


### A1 - SQL Injection:

#### A1 Steps to reproduce:

1. Login using admin/password or sam/salasana
2. Fill in any input to the first field
3. Fill in this (or similar sql statement) to a message field:
```
Second'); UPDATE Signup set message='EVIL MESSAGE'; -- -
```
4. **Issue:** All messages are updated to “EVIL MESSAGE”. Messages are visible at page http://localhost:8080/admin/admin

#### A1 Suggested fix:

It is recommended to use JPA’s standard methods instead of custom code for the database connection.

Class _SignupController_ includes suggested fixes that are currently commented out. Fix is done by removing these two lines `db_injection.addSignupToDb(name, message);` and `List<Signup> allSignups = db_injection.readSignupsFromDb();` and commenting in lines that use Spring Security's and JPA's standard methods. Class _CustomDbHelpers_ becomes obsolete and it can be removed completely. After fix code looks like this:

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String message) {
        signupRepository.save(new Signup(name, message, null));
        return "done";
    }
    
    @RequestMapping(value = "/admin/admin", method = RequestMethod.GET)
    public String loadAdmin(Model model) {
        List<Signup> allSignups = signupRepository.findAll();
        model.addAttribute("signups", allSignups);
        return "admin/admin";
    }


### A6 - Sensitive Data Exposure

#### A6 Steps to reproduce:
1. Log in
2. Fill this to a name field (message can be empty):
```
evil',(select username,password from Users where userid=1))-- -
```
3. **Issue:** Message field of a latest signup contains username and password for the user id 1. Open page http://localhost:8080/admin/admin to view login details.

#### A6 Suggested fix:

It is recommended that route to the SQL injection is fixed similarly as in the previous issue A1. In addition, passwords should be hashed. 

Class _SecurityConfiguration_ includes fix that is currently commented out. When this line is commented in, password encryption is enabled: `auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());`

After fix, hashed passwords need to be updated to the database for the default users.

### A3 - XSS attack

#### A3 Steps to reproduce:

1. Login as admin user
2. Fill in any name
3. Fill in this (or similar html) to the Message field:
```
<script>alert("Evil Message!");</script>
```
4. Open admin page http://localhost:8080/admin/admin
5. **Issue:** Page displays alert dialog

#### A3 Suggested fix:

Script becomes visible because admin view displays message as unescaped text. Fix can be done by changing `data-th-utext` to `data-th-text` in the file _admin.html_.


### A7 - Missing Function Level Access Control

#### A7 - Steps to reproduce:

1. Login as non-admin user: sam/salasana
2. Notice that form page does not display link to the admin page. This link is visible when admin user (admin/password) logs in.
3. Navigate to the admin page using direct address: http://localhost:8080/admin/admin
4. **Issue:** Basic level user has access to the admin page

#### A6 - Suggested fix:

Class _SecurityConfiguration_ contains suggested fix, where admin user level is required to access requests in the folder admin. When fix is commented in, code looks like this:

```
@Override
protected void configure(HttpSecurity http) throws Exception {
	http
    	.authorizeRequests()
        	.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
           	.anyRequest().authenticated().and()
           	.formLogin().permitAll();
}
```

It is important to notice, that authorization in the html template does not include backend validation. In other words, this line in the html template restricts only link visibility for the admin role.

```
<div sec:authorize="hasAuthority('ROLE_ADMIN')"><a href="/admin/admin">Admin page</a></div>
```


### A9 - Using Components with Known Vulnerabilities

#### A9 Steps to reproduce:

1. Open command line and navigate to a project folder
2. Run  OWASP Dependency Check using command 
```
mvn dependency-check:check
```
3. Open a dependency-check report when scan is finished. Report is located in the target folder.
4. **Issue:*** As writing this, scanner found 16 vulnerabilities and 5 vulnerable dependencies.

#### A9 Suggested fix:

Open pom.xml and update version number of a spring-boot-starter-parent to the last stable release `1.5.9.RELEASE`. Less vulnerabilities are expected when scan is re-run.
