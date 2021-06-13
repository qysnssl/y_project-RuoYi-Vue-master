package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.News;
import com.ruoyi.system.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author kdyzm
 */
@RestController
@RequestMapping("/business/news")
public class NewsController extends BaseController {


    @Autowired
    private NewsMapper newsMapper;

    /**
     * 获取新闻列表
     */
    @PreAuthorize("@ss.hasPermi('business:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(News post) {
        startPage();
        List<News> list = newsMapper.selectNewsList(post);
        return getDataTable(list);
    }

    @Log(title = "新闻管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:news:export')")
    @GetMapping("/export")
    public AjaxResult export(News post) {
        List<News> list = newsMapper.selectNewsList(post);
        ExcelUtil<News> util = new ExcelUtil<>(News.class);
        return util.exportExcel(list, "新闻数据");
    }

    /**
     * 根据新闻编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:news:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(newsMapper.selectNewsById(postId));
    }

    /**
     * 新增新闻
     */
    @PreAuthorize("@ss.hasPermi('business:news:add')")
    @Log(title = "新闻管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody News post) {
        post.setCreateBy(SecurityUtils.getUsername());
        post.setCreateTime(new Date());
        return toAjax(newsMapper.insertNews(post));
    }

    /**
     * 修改新闻
     */
    @PreAuthorize("@ss.hasPermi('business:news:update')")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody News post) {
        post.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newsMapper.updateNews(post));
    }

    /**
     * 删除新闻
     */
    @PreAuthorize("@ss.hasPermi('business:news:delete')")
    @Log(title = "新闻管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds) {
        return toAjax(newsMapper.deleteNewsByIds(postIds));
    }
}
