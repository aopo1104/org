package com.example.asus.organization2.StringData;

/**
 * Created by ASUS on 2019/2/3.
 */

public class String_NetURL {

    public static final String WEBSITEONLINE="http://10.0.2.2:8080/organization/public/index.php/index/";//这是模拟器调试用的
    //public static final String WEBSITEONLINE="http://192.168.1.106:8080/organization/public/index.php/index/"; //这是真机调试用到的
    //public static final String WEBSITEONLINE="http://134.175.128.249/organization/public/index.php/index/"; //这是服务器数据库用到的

    public static final String URL_Register = WEBSITEONLINE+"Loginregister/register";   //注册
    public static final String URL_Login = WEBSITEONLINE+"Loginregister/login"; //登录
    public static final String URL_changePsw = WEBSITEONLINE+"Changepsw/ChangePsw";//修改密码

    public static final String URL_findFriendsByPhoneNumber = WEBSITEONLINE+"Findfriends/findByPhoneNumber";    //通过手机号获得信息
    public static final String URL_findFriendsByName = WEBSITEONLINE+"Findfriends/findByNickName"; //通过昵称获得信息
    public static final String URL_findFriendsById = WEBSITEONLINE+"Findfriends/findById";                       //通过id获得信息

    public static final String URL_realVerify = WEBSITEONLINE +"Realverify/realverify"; //实名认证

    public static final String URL_createOrg = WEBSITEONLINE + "Organizationfunction/createOrg"; //创建社团
    public static final String URL_getOrgMessageById = WEBSITEONLINE + "Organizationfunction/getOrgMessageById"; //通过id查找社团

    public static final String URL_createActivity = WEBSITEONLINE + "Activityfunction/createActivity";  //创建活动
    public static final String URL_getActivityMessage= WEBSITEONLINE + "Activityfunction/getActivityMessage";  //获取活动信息

    public static final String URL_startDuty= WEBSITEONLINE + "Ondutyfunction/startDuty";  //开始值班签到
    public static final String URL_endDuty= WEBSITEONLINE + "Ondutyfunction/endDuty";  //结束值班签到

    public static final String URL_taskPublish = WEBSITEONLINE + "Taskfunction/taskPublish";  //创建任务
    public static final String URL_primaryTaskCreate = WEBSITEONLINE + "Taskfunction/primarytaskcreate";  //创建个人任务
    public static final String URL_getTaskMessage = WEBSITEONLINE + "Taskfunction/getTaskMessage";  //获取任务信息
    public static final String URL_taskRead = WEBSITEONLINE + "Taskfunction/taskRead";  //任务已阅
    public static final String URL_taskReport = WEBSITEONLINE + "Taskfunction/taskReport";  //反馈提交
    public static final String URL_getTaskMessageByPublishPersonId = WEBSITEONLINE + "Taskfunction/getTaskMessageByPublishPersonId";  //通过发布者id获取任务信息
    public static final String URL_getTaskMember = WEBSITEONLINE + "Taskfunction/getTaskMember";  //获取任务的成员
}
