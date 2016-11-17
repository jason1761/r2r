<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:include value="header.jsp"></s:include>
<!--NAVBAR SECTION END-->
<div class="home-sec" id="home">
	<div class="overlay">
		<div class="container">
			<div class="row text-center ">
				<div class="col-lg-12  col-md-12 col-sm-12">
					<div class="flexslider set-flexi" id="main-section">
						<ul class="slides move-me">
							<!-- Slider 01 -->
							<li>
								<h3>Reason 2 Run</h3>
								<h1>R2R 台中夜跑團</h1> 								<a class="btn btn-info btn-lg"  data-toggle="modal" data-target="#myModal"> 瞭解更多 </a> <a href="#order_input" class="btn btn-success btn-lg"> 預約手環毛巾 </a>
							</li>
							<!-- End Slider 01 -->
							<!-- Slider 02 -->
							<!-- <li>
								<h3>Delivering Quality Education</h3>
								<h1>UNMATCHED APPROACH</h1> <a href="#features-sec" class="btn btn-primary btn-lg"> GET AWESOME </a> <a href="#features-sec" class="btn btn-danger btn-lg"> FEATURE LIST </a>
							</li> -->
							<!-- End Slider 02 -->
							<!-- Slider 03 -->
							<!-- <li>
								<h3>Delivering Quality Education</h3>
								<h1>AWESOME FACULTY PANEL</h1> <a href="#features-sec" class="btn btn-default btn-lg"> GET AWESOME </a> <a href="#features-sec" class="btn btn-info btn-lg"> FEATURE LIST </a>
							</li> -->
							<!-- End Slider 03 -->
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">R2R台中團起跑須知</h4>
			</div>
			<div class="modal-body">
			<s:property value="@com.comm.FmtBean@getPropText('INDEX_INTRO','p_text')" escapeHtml="false"/>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
				<a class="btn btn-primary" href="http://bit.ly/R2R-Taichung" target="_blank">我要加入社團</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>



<!--HOME SECTION END-->
<div class="tag-line">
	<div class="container">
		<div class="row  text-center">
			<div class="col-lg-12  col-md-12 col-sm-12">
				<h2 data-scroll-reveal="enter from the bottom after 0.1s">
					<i class="fa fa-shield"></i><s:property value="@com.comm.FmtBean@getPropText('INDEX_INFO','p_nval')" escapeHtml="false"/> <i class="fa fa-shield"></i>
				</h2>
			</div>
		</div>
	</div>
</div>

<!-- 最新消息   -->
<%--  <div id="features-sec" class="container set-pad">
	<s:include value="features-sec.jsp" />
</div> --%>


<!-- 跑團介紹 -->
<div id="faculty-sec">
	<s:include value="faculty-sec.jsp"></s:include>
</div>


<!-- 團費公告 -->
<div id="course-sec" class="container set-pad">
	<s:include value="course-sec.jsp"></s:include>
</div>

<!-- 手環毛巾預定 -->
<div id="order_input">
	<s:include value="orderInput.jsp"></s:include>
</div>




<div class="container">
	<s:include value="container.jsp"></s:include>
</div>
<!-- CONTACT SECTION END-->
<div id="footer"></div>
<%-- ${sessionScope } --%>
<!-- FOOTER SECTION END-->
</body>
</html>