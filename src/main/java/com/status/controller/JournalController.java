package com.status.controller;

import com.status.model.QueryObj;
import com.status.model.RecallObj;
import com.status.model.ResultVo;
import com.status.service.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SevenFin on 2015/9/17.
 */
@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private IJournalService journalService;

    /**
     * @author Lenovo
     * 按条件查询
     */
    @RequestMapping("/queryByYear")
    public String queryByYear(Model model, @ModelAttribute QueryObj queryObj) {

        boolean flag = true;
        String msg = "";

        if (!isYear(queryObj.getBeginYear()) || !isYear(queryObj.getEndYear())) {
            flag = false;
            msg = "查询年份填写错误！请修改后重试";
        }
        if (flag) {
            try {
                RecallObj recallObj = new RecallObj();
                List<ResultVo> result = journalService.queryByYear(queryObj, recallObj);
                model.addAttribute("result", result);
                model.addAttribute("timeSpan", String.format("%.2f", recallObj.getTimeSpan()));
                model.addAttribute("queryObj", queryObj);

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("msg", "内部错误！请检查查询条件无误后重试");
            }
        } else {
            model.addAttribute("msg", msg);
        }
        return "journal/result";

    }

    /**
     * @author Lenovo
     * 按条件更新状态
     */
    @RequestMapping("/updateState")
    public String updateState(Model model, @ModelAttribute QueryObj queryObj) {

        try{
            RecallObj recallObj = new RecallObj();
            // 更新链接库状态
            journalService.updateState(queryObj, recallObj);

            // 重新查询出结果
            List<ResultVo> result = journalService.queryByYear(queryObj, recallObj);
            model.addAttribute("result", result);
            System.out.println(recallObj.getTimeSpan());
            model.addAttribute("timeSpan", String.format("%.2f", recallObj.getTimeSpan()));
            model.addAttribute("queryObj", queryObj);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "内部错误！请检查查询条件无误后重试");
            return "journal/result";
        }

        model.addAttribute("updateMsg", "重置状态成功");
        return "journal/result";
    }
    /**
     * @author Lenovo
     * 按条件更新状态 带stateTo
     */
    @RequestMapping("/updateTargetState")
    public String updateTargetState(Model model, @ModelAttribute QueryObj queryObj) {

        try{
            RecallObj recallObj = new RecallObj();
            // 更新链接库状态
            journalService.updateState(queryObj, recallObj);

            model.addAttribute("timeSpan", String.format("%.2f", recallObj.getTimeSpan()));
            model.addAttribute("queryObj", queryObj);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "内部错误！请检查查询条件无误后重试");
            return "journal/result";
        }

        model.addAttribute("msg", "重置状态成功");
        return "journal/result";
    }

    public boolean isYear(String str) {
        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
