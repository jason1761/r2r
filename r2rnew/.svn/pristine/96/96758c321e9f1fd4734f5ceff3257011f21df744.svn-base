<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="overlay">
	<div class="container set-pad">
		<div class="row text-center">
			<div class="col-lg-8 col-lg-offset-2 col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2">
				<h1 data-scroll-reveal="enter from the bottom after 0.1s" class="header-line">毛巾手環預定</h1>
				<p data-scroll-reveal="enter from the bottom after 0.3s">填入資料，讓我們一起擁有專屬我們的回憶。</p>
			</div>
		</div>
		<!--/.HEADER LINE END-->
		<div class="row set-row-pad" data-scroll-reveal="enter from the bottom after 0.5s">
			<s:if test="#session.user_id !=null">
				<div class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2">
					<div id='formMsg' style="display: none"></div>
					<form id="form" name="form" action="OrderAction.html" method="post">
						<s:token />
						<div class="form-group">
							<input type="text" class="form-control " required="required" placeholder="姓名" name="fb_name" value="${session.fb_name }" readOnly />
						</div>
						<div class="form-group">
							<input type="text" class="form-control " required="required" placeholder="Email" name="email" value="${session.email }" readOnly />
						</div>
						<div class="form-group">
							<input type="tel" class="form-control " required="required" placeholder="手機號碼(請輸入09開頭之台灣行動電話號碼)" name="phone" pattern="[0][9][0-9]{8}" />
						</div>
						<div class="form-group">
							<input type="text" class="form-control " required="required" placeholder="真實姓名" name="cname" maxlength="10"/>
						</div>
						<div class="form-group col-lg-6 col-sm-6">
							<label>毛巾訂購</label>
							<input type="number" class="form-control " required="required" placeholder="填入數量 (每條 $150)" name="order_towel" min="0" max="5" />
						</div>

						<div class="form-group col-lg-6 col-sm-6">
							<label>手環訂購</label>
							<input type="number" class="form-control " required="required" placeholder="填入數量 (每條 $100,含5顆電池)" name="order_bracelet" min="0" max="5" />
						</div>

						<div class="form-group col-lg-6 col-sm-6">
							<label>取貨時間</label>
							<div id="datetimepicker" class="form-group input-group">
								<input data-format="yyyy/MM/dd" type="text" class="form-control" id="rel_get_date" name="get_date" required="required" placeholder="YYYY/MM/DD"></input>
								<span class="add-on input-group-addon">
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="fa fa-calendar"> </i>
								</span>
							</div>
						</div>
						<div class="form-group col-lg-6 col-sm-6">
							<label>取貨地點</label>
							<s:select class="form-control" name="get_place" list="@com.comm.FmtBean@getSysCDMap(\"place\",\"and show_on = 'Y' order by sub_code \")" listKey="key" listValue="value" />
						</div>

						<div class="form-group">
							<textarea name="memo" class="form-control" style="min-height: 150px;" placeholder="想說的話"></textarea>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-info btn-block btn-md" onclick="submitForm()">送出預定</button>
						</div>
					</form>
				</div>
			</s:if>
			<s:else>
				<div class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2">
					<div class="form-group">
						<a class="btn btn-block btn-social btn-facebook" href="#order_input" onclick="facebookLogin()">
							<i class="fa fa-facebook"></i> 使用Facebook登入
						</a>
					</div>
				</div>
			</s:else>
		</div>
	</div>
</div>
<script>
	$('#datetimepicker').datetimepicker({
		language : 'zh_TW',
		pickTime : false
	});

	$(function() {
		var msgType = "<s:property value='msgType' escapeHtml='false'/>";
		if (msgType != '') {
			$('html, body').animate({
				scrollTop : $('#order_input').offset().top
			}, 'slow');
			
			
			if(msgType == 'upd'){
				$('#formMsg').html('預約成功。');
				$('#formMsg').addClass("alert alert-success");
			}else if(msgType == 'err'){
				$('#formMsg').html('預約失敗，請洽系統管理員或各團(副)長');
				$('#formMsg').addClass("alert alert-error");
			}
			$('#formMsg').show();
		}else{
			$('#formMsg').removeClass();
			$('#formMsg').hide();
		}
	});

	
</script>