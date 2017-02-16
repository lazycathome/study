package cn.bigdb.validation.boot;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bigdb.validation.boot.Student.AGE;
import cn.bigdb.validation.boot.Student.NAME;


@RestController
public class ValidateController {

	@RequestMapping(value="testStudent")
    public void testStudent(@Validated Student student) {

    }


    @RequestMapping(value="testStudent1")
    public void testStudent1(@Validated(NAME.class) Student student) {

    }

    @RequestMapping(value="testStudent2")
    public void testStudent2(@Validated({NAME.class,AGE.class}) 
    Student student) {

    }
}
