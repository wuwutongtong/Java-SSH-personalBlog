package com.blacklee.blogs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blacklee.admin.entity.Administrator;
import com.blacklee.admin.entity.Blogs;
import com.blacklee.blogs.service.FrontendAdministratorService;
import com.blacklee.blogs.service.FrontendBlogsService;
import com.blacklee.blogs.service.FrontendClassificationService;
import com.blacklee.blogs.service.FrontendTypeService;

@Controller
public class FrontendBlogsController {
	@Autowired
	private FrontendBlogsService frontendBlogsService;
	@Autowired
	private FrontendAdministratorService frontendAdministratorService;
	@Autowired
	private FrontendTypeService frontendTypeService;
	@Autowired
	private FrontendClassificationService frontendClassificationService;
	
	@RequestMapping(value="/getAll/{id}", method=RequestMethod.GET)
	public String getAllMsg(@PathVariable("id") Integer id, @RequestParam(value="pageIndex", required=false) Integer pageIndex, HttpServletRequest request){
		Integer maxResult = 20;
		List<Blogs> blogs = null;
		if(pageIndex == null){
			blogs = frontendBlogsService.getBlogs(20, 0);
		}else{
			blogs = frontendBlogsService.getBlogs(maxResult, pageIndex);
		}
		request.setAttribute("blogs", blogs);
		Administrator admin = frontendAdministratorService.getUserInfo(id);
		request.setAttribute("admin", admin);
		Long typeOriginalSum = frontendTypeService.getOriginal();
		request.setAttribute("typeOriginalSum", typeOriginalSum);
		Long typeCopySum = frontendTypeService.getCopy();
		request.setAttribute("typeCopySum", typeCopySum);
		Long visitSum = frontendBlogsService.getVistSum();
		request.setAttribute("visitSum", visitSum);
		Long supportSum = frontendBlogsService.getSupportSum();
		request.setAttribute("supportSum", supportSum);
		Integer pagesCount = (int) Math.ceil((float)frontendBlogsService.getBlogsCount() / maxResult);
		request.setAttribute("pagesCount", pagesCount);
		List<String> classificationName = frontendClassificationService.getClassificationName();
		request.setAttribute("classificationName", classificationName);
		List<Object> titleByVisit = frontendBlogsService.getTitleByVisit();
		request.setAttribute("titleByVisit", titleByVisit);
		List<Object> titleBySupport = frontendBlogsService.getTitleBySupport();
		request.setAttribute("titleBySupport", titleBySupport);
		List<Object> titleById = frontendBlogsService.getTitleById();
		request.setAttribute("titleById", titleById);
		return "frontend.blogs.list";
	}
}