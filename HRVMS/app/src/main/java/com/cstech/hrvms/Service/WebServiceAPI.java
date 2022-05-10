package com.cstech.hrvms.Service;

import android.telecom.Call;

import com.cstech.hrvms.Adapters.AddEventImages;
import com.cstech.hrvms.DataModels.AddEmployeeDataModel;
import com.cstech.hrvms.DataModels.AddVendorDataModel;
import com.cstech.hrvms.DataModels.ChangePasswordDataModel;
import com.cstech.hrvms.DataModels.EmployeeAddDataModel;
import com.cstech.hrvms.DataModels.SummaryCreateTimesheetDataModel;
import com.cstech.hrvms.Models.AddEmployeeModel;
import com.cstech.hrvms.Models.AddVendorModel;
import com.cstech.hrvms.Models.ChangePasswordModel;
import com.cstech.hrvms.Models.ClientInvoiceModel;
import com.cstech.hrvms.Models.Country;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.EmployeeAddModel;
import com.cstech.hrvms.Models.EmployeeClientDetailsModel;
import com.cstech.hrvms.Models.EventsImages;
import com.cstech.hrvms.Models.FindEmployeeModel;
import com.cstech.hrvms.Models.ForgetPasswordModel;
import com.cstech.hrvms.Models.GetClientVendorModel;
import com.cstech.hrvms.Models.GetCountry;
import com.cstech.hrvms.Models.GetEducationsModel;
import com.cstech.hrvms.Models.GetJobTitlesModel;
import com.cstech.hrvms.Models.GetSecurityProfileModel;
import com.cstech.hrvms.Models.GetUsersModel;
import com.cstech.hrvms.Models.Holidays;
import com.cstech.hrvms.Models.HolidaysList;
import com.cstech.hrvms.Models.MultipleTimeSheets;
import com.cstech.hrvms.Models.News;
import com.cstech.hrvms.Models.SendMailModel;
import com.cstech.hrvms.Models.SummaryCreateTimesheetModel;
import com.cstech.hrvms.Models.UserLoginModel;
import com.cstech.hrvms.Models.ViewTimeSheetModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


public interface WebServiceAPI {

    @GET("/Login/{username}/{password}")//Login Activity
    void getLogin(@Path("username") String username,
                  @Path("password") String password,
                  Callback<UserLoginModel> Callback);

    @GET("/json")
    void getCountry(Callback<GetCountry>callback);

    @POST("/AddSummaryTimeSheets/{id}")//Add Summary Create Timesheet
    void postSummaryCreateTimeSheet(@Path("id") String id,@Body SummaryCreateTimesheetDataModel summaryCreateTimesheetDataModel,
                                    Callback<SummaryCreateTimesheetModel> Callback);

    @GET("/getTimeSheet/{id}/{fromdate}/{todate}")//View Timesheet
    void ViewTimeSheet(@Path("id") String id,@Path("fromdate") String fromdate,
                       @Path("todate") String todate, Callback<ViewTimeSheetModel> Callback);

    @GET("/getClientdetails/{id}")//Employee Client Details
    void GetClientVendorDetails(@Path("id") String id, Callback<EmployeeClientDetailsModel> Callback);


    @GET("/getUsers")//To Get Employee Names
    void GetUsers(Callback<GetUsersModel> Callback);

    @GET("/getClients")//To Get Client Names
    void GetClients(Callback<GetClientVendorModel> Callback);

    @GET("/getVendors")//To Get Vendor Names
    void GetVendors(Callback<GetClientVendorModel> Callback);

    @POST("/AddClientNVendors/{id}")//Add Client and Add Vendor
    void PostVendorDetails(@Path("id") String id,@Body AddVendorDataModel addVendorDataModel,
                           Callback<AddVendorModel> Callback);


    @GET("/getEmployees")//Find Employees
    void FindEmployeeList(Callback<FindEmployeeModel> Callback);

    @POST("/AddEmployeeWorkInfo/{userId}")//Add Employees Work Info
    void AddEmployeeWorkInfo(@Path("userId") String userId,@Body AddEmployeeDataModel addEmployeeDataModel,
                             Callback<AddEmployeeModel> Callback);

    @PUT("/UpdateEmployeeWorkInfo/{userId}")//Update Employees Work Info
    void UpdateEmployeeWorkInfo(@Path("userId") String userId,@Body AddEmployeeDataModel addEmployeeDataModel,
                             Callback<AddEmployeeModel> Callback);


    @DELETE("/deleteEmpWorkInfo/{Id}")//Delete Employee Details
    void DeleteEmployeeWorkInfo(@Path("Id") String id,Callback<DeleteEmployeeWorkInfoModel> Callback);

    @GET("/getInvoices")//Get Invoices
    void GetInvoiceDetails(Callback<ClientInvoiceModel> Callback);


    @GET("/getEducations")//To Get Educations
    void GetEducations(Callback<GetEducationsModel> Callback);

    @GET("/getJobTitles")//To Get Job Titles
    void GetJobTitles(Callback<GetJobTitlesModel> Callback);

    @GET("/getSecurityProfile")//To Get Security details
    void GetSecurityProfile(Callback<GetSecurityProfileModel> Callback);


    @PUT("/changePassword")//Change Password
    void UpdatePassword(@Body ChangePasswordDataModel changePasswordDataModel,
                                Callback<ChangePasswordModel> Callback);

    @POST("/AddEmployee")//Add Summary Create Timesheet
    void PostAddEmployee(@Body EmployeeAddDataModel employeeAddDataModel,
                                    Callback<EmployeeAddModel> Callback);


    @GET("/ForgotPassword/{emailId}")//Forget Password
    void PostForgetPassword(@Path("emailId") String emailId, Callback<ForgetPasswordModel> Callback);


    @POST("/AddMultipleTimeSheets/{id}")//Add Detailed Create Timesheet
    void AddMultipleTimesheets(@Path("id") String id,@Body List<SummaryCreateTimesheetDataModel> summaryCreateTimesheetDataModel, Callback< SummaryCreateTimesheetModel > Callback);

    //AddNewMultipleTimeSheets/{id}
    @POST("/AddNewMultipleTimeSheets/{id}")//Add Detailed Create Timesheet
    void AddNewMultipleTimesheets(@Path("id") String id, @Body MultipleTimeSheets summaryCreateTimesheetDataModel, Callback< SummaryCreateTimesheetModel > Callback);

    @GET("/sendTimeSheetDetails/{id}/{userId}")//Sent Mail to Hr
    void SendMailToHR(@Path("id") String id,@Path("userId") String userId,
                      Callback<SendMailModel> Callback);

    @GET("/getHolidays")
    void  getHolidays(Callback<Holidays>holidaysCallback);

    @DELETE("/DeleteHoliday/{Id}")
    void deleteHolidays(@Path("Id") int id, Callback<DeleteEmployeeWorkInfoModel>delete);

    @PUT("/UpdateHolidays/{Id}")
    void updateHolidays(@Path("Id") int id,@Body HolidaysList holidaysList,
                        Callback<DeleteEmployeeWorkInfoModel> update);

    @POST("/AddHolidays")
    void  addHolidays(@Body HolidaysList holidaysList, Callback<DeleteEmployeeWorkInfoModel>update);
    @GET("/getCountrys")
    void  getCountries(Callback<Country> countryCallback);
    @GET("/getNews")
    void getNews(Callback<News>newsCallback);
    @GET("/getEventsImages/{newsId}")
    void getEventsImages(@Path("newsId") int newsId,Callback<EventsImages> callback);

    @POST("/addEventsImages/{newsId}")
    void addEventImages(@Path("newsId") int newsId,
                        @Body AddEventImages eventImages,
                        Callback<DeleteEmployeeWorkInfoModel> callback );

    @DELETE("/DeleteEventPhoto/{id}")
    void deleteEventPhoto(@Path("id") int id, Callback<DeleteEmployeeWorkInfoModel> callback);

    @POST("/addNews/{userId}")
    void addNews(@Path("userId") int id,@Body News.NewsList news, Callback<DeleteEmployeeWorkInfoModel> callback);

    @POST("/updateNews/{userId}")
    void updateNews(@Path("userId") int id,@Body News.NewsList news, Callback<DeleteEmployeeWorkInfoModel> callback);

    @DELETE("/deleteNews/{id}")
    void deleteNews(@Path("id") int id, Callback<DeleteEmployeeWorkInfoModel> callback);

}
