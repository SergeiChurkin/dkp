insert into user (active, coefficient, confirm, email, level_id, password, points, prof_id, reg_screenshot, requested, token, username, validate)
values (1, 0, 1, NULL , NULL , "$2a$08$PKMFnMNOsDZ/86mnZkLn7eM/Q.hyTdzzvIE.dFDYhuVMZKOl991Fi", 0, NULL , NULL , 0, NULL , "admin", 0);

insert into user_role (user_id, roles) values (1, "ADMIN");