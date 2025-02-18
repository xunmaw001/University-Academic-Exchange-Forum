package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.BanzhutuijiantieEntity;
import com.entity.view.BanzhutuijiantieView;

import com.service.BanzhutuijiantieService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 版主推荐贴
 * 后端接口
 * @author 
 * @email 
 * @date 2021-04-10 13:21:45
 */
@RestController
@RequestMapping("/banzhutuijiantie")
public class BanzhutuijiantieController {
    @Autowired
    private BanzhutuijiantieService banzhutuijiantieService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,BanzhutuijiantieEntity banzhutuijiantie, 
		HttpServletRequest request){

        EntityWrapper<BanzhutuijiantieEntity> ew = new EntityWrapper<BanzhutuijiantieEntity>();
		PageUtils page = banzhutuijiantieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, banzhutuijiantie), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BanzhutuijiantieEntity banzhutuijiantie, HttpServletRequest request){
        EntityWrapper<BanzhutuijiantieEntity> ew = new EntityWrapper<BanzhutuijiantieEntity>();
		PageUtils page = banzhutuijiantieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, banzhutuijiantie), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BanzhutuijiantieEntity banzhutuijiantie){
       	EntityWrapper<BanzhutuijiantieEntity> ew = new EntityWrapper<BanzhutuijiantieEntity>();
      	ew.allEq(MPUtil.allEQMapPre( banzhutuijiantie, "banzhutuijiantie")); 
        return R.ok().put("data", banzhutuijiantieService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BanzhutuijiantieEntity banzhutuijiantie){
        EntityWrapper< BanzhutuijiantieEntity> ew = new EntityWrapper< BanzhutuijiantieEntity>();
 		ew.allEq(MPUtil.allEQMapPre( banzhutuijiantie, "banzhutuijiantie")); 
		BanzhutuijiantieView banzhutuijiantieView =  banzhutuijiantieService.selectView(ew);
		return R.ok("查询版主推荐贴成功").put("data", banzhutuijiantieView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BanzhutuijiantieEntity banzhutuijiantie = banzhutuijiantieService.selectById(id);
		banzhutuijiantie.setClicknum(banzhutuijiantie.getClicknum()+1);
		banzhutuijiantieService.updateById(banzhutuijiantie);
        return R.ok().put("data", banzhutuijiantie);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BanzhutuijiantieEntity banzhutuijiantie = banzhutuijiantieService.selectById(id);
		banzhutuijiantie.setClicknum(banzhutuijiantie.getClicknum()+1);
		banzhutuijiantieService.updateById(banzhutuijiantie);
        return R.ok().put("data", banzhutuijiantie);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        BanzhutuijiantieEntity banzhutuijiantie = banzhutuijiantieService.selectById(id);
        if(type.equals("1")) {
        	banzhutuijiantie.setThumbsupnum(banzhutuijiantie.getThumbsupnum()+1);
        } else {
        	banzhutuijiantie.setCrazilynum(banzhutuijiantie.getCrazilynum()+1);
        }
        banzhutuijiantieService.updateById(banzhutuijiantie);
        return R.ok();
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BanzhutuijiantieEntity banzhutuijiantie, HttpServletRequest request){
    	banzhutuijiantie.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(banzhutuijiantie);

        banzhutuijiantieService.insert(banzhutuijiantie);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BanzhutuijiantieEntity banzhutuijiantie, HttpServletRequest request){
    	banzhutuijiantie.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(banzhutuijiantie);

        banzhutuijiantieService.insert(banzhutuijiantie);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BanzhutuijiantieEntity banzhutuijiantie, HttpServletRequest request){
        //ValidatorUtils.validateEntity(banzhutuijiantie);
        banzhutuijiantieService.updateById(banzhutuijiantie);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        banzhutuijiantieService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<BanzhutuijiantieEntity> wrapper = new EntityWrapper<BanzhutuijiantieEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = banzhutuijiantieService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
