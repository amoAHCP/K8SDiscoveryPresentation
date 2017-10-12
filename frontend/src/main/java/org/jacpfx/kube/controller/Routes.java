package org.jacpfx.kube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by amo on 22.06.17.
 */
@Controller
 public class Routes {
  @RequestMapping({
      "/"
  })
  public String index() {
    return "/index.html";
  }
}
