package com.dream.ems.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.dream.ems.dto.StudentDto;
import com.dream.ems.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dream.ems.dto.ClazzDto;
import com.dream.ems.service.ClazzService;
import wo.bsys.vo.WoDataTable;
import com.dream.ems.vo.WoSelectorParams;
import wo.common.entity.WoPage;
import wo.common.exception.WoResultCode;
import wo.common.util.WoJsonUtil;
import wo.bsys.util.BSysConstant;

/**
 * 班级控制器.
 * @author cailei
 */
@Controller
@RequestMapping("/ems/clazz")
@SessionAttributes({BSysConstant.SESSION_USER,"studentInfo","clazz"})
public class ClazzController {

	/**
	 * 注入ClazzService.
	 */
    @Resource // @Autowired
	private ClazzService clazzService;

	@Resource
	private StudentService studentService;
	/**
	 * @param draw DataTable控件请求序列号,返回数据时需要设置该值
	 * @param start 当前页开始索引,从0开始
	 * @param length 当前页最大行数
	 * @param searchContent 查询内容
	 * @param dir 排序方式
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public WoDataTable<ClazzDto> getDataTable(Integer draw, Long start, Long length,
			@RequestParam("search[value]") String searchContent, @RequestParam("order[0][dir]") String dir
            			) {
		WoPage<ClazzDto> page = clazzService.getPageData(start, length, searchContent, dir
				);
		return new WoDataTable<ClazzDto>(page, draw);
	}
	
	/**
	 * 加载"创建"页面
	 * @return
	 */
	@GetMapping("/create")
	public String create (Map<String, Object> map
				) {
		map.put("url", "clazz/create.jsp");
		
				return "main";
	}
	
	/**
	 * 提交"创建"表单
	 * @return
	 */
	@PostMapping("/create")
	public String create (ClazzDto dto, Map<String, Object> map) {
		clazzService.create(dto);
				return "redirect:/";
			}
	
	/**
	 * 加载"修改"页面
	 * @return
	 */
	@GetMapping("/update")
	public String update (String id, Map<String, Object> map) {
		ClazzDto dto = clazzService.getById (id);
		map.put("url", "clazz/update.jsp");
		map.put("formData", dto);
		return "main";
	}
	/**
	 * 加载"查看班级所有学生"页面
	 * @return
	 */
	@GetMapping("/showStudents")
	public String showStudents (String id, Map<String, Object> map) {
		ClazzDto dto = clazzService.getById (id);
		map.put("url", "clazz/students.jsp");
		map.put("clazz", dto);
		return "main";
	}
	@RequestMapping("/listStudents")
	@ResponseBody
	public WoDataTable<StudentDto> getDataTable(Integer draw, Long start, Long length,
												@RequestParam("search[value]") String searchContent, @RequestParam("order[0][dir]") String dir, Map<String, Object> map) {
		ClazzDto dto = (ClazzDto) map.get("clazz");
		WoPage<StudentDto> page = studentService.getPageData(dto,start, length, searchContent, dir);
		return new WoDataTable<StudentDto>(page, draw);
	}
	/**
	 * 提交"修改"表单
	 * @return
	 */
	@PostMapping("/update")
	public String update (ClazzDto dto, Map<String, Object> map) {
		clazzService.update(dto);
				return "redirect:/";
			}
	
	/**
	 * @param id
	 * @return
	 */
	@PostMapping ("/delete")
	@ResponseBody
	public WoResultCode delete (String[] id) {
		clazzService.delete(id);
		return WoResultCode.getSuccessCode().setMsg("删除班级成功!");
	}
	
		/**
	 * 加载"选择器"页面
	 * @return
	 */
	@PostMapping("/selector")
	public String loadSelector (WoSelectorParams params, Map<String, Object> map) {
		map.put("selectorParams", WoJsonUtil.toString(params));
		return "clazz/selector";
	}
	
	/**
	 * 加载"选择器"列表数据
	 * @param draw DataTable控件请求序列号,返回数据时需要设置该值
	 * @param start 当前页开始索引,从0开始
	 * @param length 当前页最大行数
	 * @param searchContent 查询内容
	 * @param dir 排序方式
	 * @return
	 */
	@RequestMapping("/selector/list")
	@ResponseBody
	public WoDataTable<ClazzDto> getSelectorDataTable(Integer draw, Long start, Long length,
			@RequestParam("search[value]") String searchContent, @RequestParam("order[0][dir]") String dir,String params) {
		WoPage<ClazzDto> page = clazzService.getPageData(start, length, searchContent, dir,params);
		return new WoDataTable<ClazzDto>(page, draw);
	}
	}
