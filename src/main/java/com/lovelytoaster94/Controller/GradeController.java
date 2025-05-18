package com.lovelytoaster94.Controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lovelytoaster94.Pojo.Course;
import com.lovelytoaster94.Pojo.CourseTask;
import com.lovelytoaster94.Pojo.GPA;
import com.lovelytoaster94.Pojo.Grade;
import com.lovelytoaster94.Service.CourseService;
import com.lovelytoaster94.Service.CourseTaskService;
import com.lovelytoaster94.Service.GradeService;
import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import com.lovelytoaster94.Until.ManagementResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {
    private final GradeService gradeService;
    private final CourseTaskService courseTaskService;
    private final CourseService courseService;
    private final ManagementResultInfo managementResultInfo = new ManagementResultInfo();

    public GradeController(GradeService gradeService, CourseTaskService courseTaskService, CourseService courseService) {
        this.gradeService = gradeService;
        this.courseTaskService = courseTaskService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result allGradeInfo() {
        return managementResultInfo.allInfo(gradeService.allGradeInfo());
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyGradeInfo(Grade grade) {
        int usualGrade = grade.getUsualGrade();
        int finalExamGrade = grade.getFinalExamGrade();
        int courseGrade = (int) (usualGrade * 0.4 + finalExamGrade * 0.6);
        grade.setTotalGrade(courseGrade);
        double gpa;

        if (courseGrade >= 90) {
            gpa = 4.0;
        } else if (courseGrade >= 85) {
            gpa = 3.7;
        } else if (courseGrade >= 82) {
            gpa = 3.3;
        } else if (courseGrade >= 78) {
            gpa = 3.0;
        } else if (courseGrade >= 75) {
            gpa = 2.7;
        } else if (courseGrade >= 72) {
            gpa = 2.3;
        } else if (courseGrade >= 68) {
            gpa = 2.0;
        } else if (courseGrade >= 66) {
            gpa = 1.7;
        } else if (courseGrade >= 64) {
            gpa = 1.3;
        } else if (courseGrade >= 60) {
            gpa = 1.0;
        } else {
            gpa = 0.0;
        }

        grade.setCoursePoint(gpa);

        boolean verify = gradeService.modifyGradeInfo(grade);
        return managementResultInfo.modifyInfo(verify, grade);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteGradeInfo(Grade grade, CourseTask courseTask) {
        CourseTask courseTaskSearch = courseTaskService.searchCourseTaskInfo(courseTask).getFirst();

        if (!checkTime(courseTaskSearch.getStartTime(), courseTaskSearch.getEndTime())) {
            return new Result(Code.DELETE_FAILED, "该课程不在允许的选课时间范围内");
        }

        boolean data = gradeService.deleteGradeInfo(grade.getNo());
        return managementResultInfo.deleteInfo(data);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result searchGradeInfo(Grade grade) {
        List<Grade> data = gradeService.searchGradeInfo(grade);
        return managementResultInfo.searchInfo(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addGradeInfo(Grade grade, CourseTask courseTask) {
        CourseTask courseTaskSearch = courseTaskService.searchCourseTaskInfo(courseTask).getFirst();

        if (!checkTime(courseTaskSearch.getStartTime(), courseTaskSearch.getEndTime())) {
            return new Result(Code.ADD_FAILED, "该课程不在允许的选课时间范围内");
        }

        boolean isIncluded = Arrays.asList(courseTaskSearch.getCourse().split(",")).contains(grade.getCourseNo());

        if (!isIncluded) {
            return new Result(Code.ADD_FAILED, "该课程不在允许的选课范围内");
        }

        Grade searchGrade = new Grade();
        searchGrade.setStudentNo(grade.getStudentNo());
        List<Grade> courseList = gradeService.searchGradeInfo(searchGrade);
        Course course = new Course();
        course.setCourseNo(grade.getCourseNo());
        Course currentCourse = courseService.searchCourseInfo(course).getFirst();

        for (Grade c : courseList) {
            if (c.getCourseName().equals(currentCourse.getCourseName()) && !c.getCourseNo().equals(currentCourse.getCourseNo())) {
                return new Result(Code.ADD_FAILED, "课程重复，不允许选择");
            }
        }

        grade.setTerm(courseTaskSearch.getTerm());

        boolean verify = gradeService.addGradeInfo(grade);
        return managementResultInfo.addInfo(verify, grade);
    }

    @RequestMapping(value = "/getGPA", method = RequestMethod.GET)
    @ResponseBody
    public Result getGPA(@RequestParam("studentNo") String studentNos, @RequestParam(value = "teacherNo", required = false) String teacherNo) {
        JSONObject jsonObject = new JSONObject();
        List<GPA> gpaList = new ArrayList<>();
        Map<String, List<Grade>> termGradeMap = new HashMap<>();

        List<String> studentNoList = Arrays.asList(studentNos.split(","));
        boolean needFilterByTeacher = studentNoList.size() > 1;

        for (String studentNo : studentNoList) {
            Grade grade = new Grade();
            grade.setStudentNo(studentNo.trim());
            List<Grade> data = gradeService.searchGradeInfo(grade);

            for (Grade item : data) {
                if (!needFilterByTeacher || teacherNo.equals(item.getTeacherNo())) {
                    String term = item.getTerm();
                    if (!termGradeMap.containsKey(term)) {
                        termGradeMap.put(term, new ArrayList<>());
                    }
                    termGradeMap.get(term).add(item);
                }
            }
        }

        double totalGpa = 0;
        int gpaTermCount = 0;

        for (Map.Entry<String, List<Grade>> entry : termGradeMap.entrySet()) {
            String term = entry.getKey();
            List<Grade> grades = entry.getValue();
            double gpa = 0;
            double credit = 0;
            boolean hasInvalidGrade = false;

            for (Grade gradeItem : grades) {
                if (gradeItem.getCoursePoint() == -1) {
                    hasInvalidGrade = true;
                }
                credit += gradeItem.getCourseGrade();

                if (!hasInvalidGrade) {
                    gpa += gradeItem.getCoursePoint() * gradeItem.getCourseGrade();
                }
            }

            GPA gpaItem = new GPA();
            gpaItem.setTerm(term);
            gpaItem.setCourseGrade((int) credit);

            if (hasInvalidGrade || credit == 0) {
                gpaItem.setGpa(-1.0);
            } else {
                double termGpa = gpa / credit;
                gpaItem.setGpa(Double.parseDouble(String.format("%.2f", termGpa)));
                totalGpa += termGpa;
                gpaTermCount++;
            }

            gpaList.add(gpaItem);
        }

        double averageGPA = gpaTermCount > 0 ? totalGpa / gpaTermCount : 0;
        jsonObject.put("averageGPA", Double.parseDouble(String.format("%.2f", averageGPA)));
        jsonObject.put("GPA", gpaList);

        return new Result(Code.SEARCH_SUCCESS, "查询成功", jsonObject);
    }


    @RequestMapping(value = "/getGradeStatistics", method = RequestMethod.GET)
    @ResponseBody
    public Result getGradeStatistics(@RequestParam("studentNo") String studentNos, @RequestParam(value = "teacherNo", required = false) String teacherNo, @RequestParam(value = "term", required = false) String term) {
        List<String> studentNoList = Arrays.asList(studentNos.split(","));
        List<Grade> allGrades = new ArrayList<>();

        boolean filterByTeacher = studentNoList.size() > 1;

        for (String studentNo : studentNoList) {
            Grade grade = new Grade();
            grade.setStudentNo(studentNo.trim());
            List<Grade> data = gradeService.searchGradeInfo(grade);

            for (Grade g : data) {
                if ((!filterByTeacher || (g.getTeacherNo() != null && g.getTeacherNo().equals(teacherNo)))
                        && (term == null || (g.getTerm() != null && g.getTerm().equals(term)))) {
                    allGrades.add(g);
                }
            }
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject gradeStatistics;
        String[] gradeName = {"90-100", "80-89", "70-79", "60-69", "不及格"};
        int[] gradeCount = new int[5];

        List<Grade> validGrades = new ArrayList<>();

        for (Grade item : allGrades) {
            double gradeValue = item.getTotalGrade();
            if (gradeValue < 0 || gradeValue > 100) continue;

            validGrades.add(item);

            if (gradeValue >= 90) {
                gradeCount[0]++;
            } else if (gradeValue >= 80 && gradeValue <= 89) {
                gradeCount[1]++;
            } else if (gradeValue >= 70 && gradeValue <= 79) {
                gradeCount[2]++;
            } else if (gradeValue >= 60 && gradeValue <= 69) {
                gradeCount[3]++;
            } else if (gradeValue <= 59) {
                gradeCount[4]++;
            }
        }

        for (int i = 0; i < gradeName.length; i++) {
            if (gradeCount[i] == 0) continue;
            gradeStatistics = new JSONObject();
            gradeStatistics.put("item", gradeName[i]);
            gradeStatistics.put("count", gradeCount[i]);
            gradeStatistics.put("percent", Double.parseDouble(String.format("%.2f", (double) gradeCount[i] / validGrades.size())));
            jsonArray.add(gradeStatistics);
        }

        return new Result(Code.SEARCH_SUCCESS, "查询成功", jsonArray);
    }

    private boolean checkTime(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startTimeLocal = LocalDate.parse(startTime, formatter);
        LocalDate endTimeLocal = LocalDate.parse(endTime, formatter);
        LocalDate currentTime = LocalDate.now();

        return !currentTime.isBefore(startTimeLocal) && !currentTime.isAfter(endTimeLocal);
    }
}
