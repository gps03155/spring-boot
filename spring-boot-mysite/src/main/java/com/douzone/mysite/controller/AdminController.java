package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@Auth(Role.ADMIN)
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.select());
		
		return "admin/main";
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(@ModelAttribute SiteVo siteVo, @RequestParam(value="upload-profile") MultipartFile multipartFile) {
		String url = fileuploadService.restore(multipartFile);
		siteVo.setProfile(url);
		
		siteService.update(siteVo);
		
		return "redirect:/";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/guestbook")
	public String guestBook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
