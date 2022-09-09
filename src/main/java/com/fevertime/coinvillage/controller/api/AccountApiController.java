package com.fevertime.coinvillage.controller.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "통장관리")
@RequestMapping("accounts")
public class AccountApiController {

}
