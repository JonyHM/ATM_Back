<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Hello, world!</title>
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="/css/bootstrap.min.css">
	</head>
	<body ng-app="atm" >
		<h1>ATM</h1>
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item">
				<a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Bank</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Account</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Operation</a>
			</li>
		</ul>
		<div class="tab-content" id="tabBank" ng-controller="bankController as bkCtrl" >
			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
				<div class="col-4" >
					<form>
						<div class="form-group">
							<label class="col-form-label" for="formGroupExampleInput">Bank Name</label>
							<input type="text" class="form-control" id="formGroupExampleInput" ng-model="bkCtrl.bankName" placeholder="Type bank name">
						</div>
						<button class="btn btn-primary" ng-click="bkCtrl.saveBank()" >Save</button>
					</form>
				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
				<div class="col-4" >
					<form>
						<div class="form-group">
							<label class="col-form-label" for="formGroupExampleInput">Select Bank</label>
							<select class="form-control" id="exampleFormControlSelect1">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</div>
						<div class="form-group">
							<label class="col-form-label" for="formGroupExampleInput">Account number:</label>
							<input type="text" class="form-control" id="formGroupExampleInput" >
						</div>
						<div class="form-group">
							<label class="col-form-label" for="formGroupExampleInput">Owner name</label>
							<input type="text" class="form-control" id="formGroupExampleInput">
						</div>
						<div class="form-group">
							<label class="col-form-label" for="formGroupExampleInput">balance</label>
							<input type="text" class="form-control" id="formGroupExampleInput">
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-form-label">Password</label>
							<input type="password" class="form-control" id="inputPassword3" placeholder="Password">
  						</div>
						
						<button type="submit" class="btn btn-primary">Save</button>
					</form>
				</div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				<div class="col-4" >
					<div>
						<div>
							<div id="firstItem" >
								<form>
									<div class="form-group">
										<label class="col-form-label" for="formGroupExampleInput">Select Bank</label>
										<select class="form-control" id="exampleFormControlSelect1">
											<option>Itau</option>
											<option>Banco do Brasil</option>
											<option>Santender</option>
										</select>
									</div>
									<div class="form-group">
										<label class="col-form-label" for="formGroupExampleInput">Account number:</label>
										<input type="text" class="form-control" id="formGroupExampleInput" >
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-form-label">Password</label>
										<input type="password" class="form-control" id="inputPassword3" placeholder="Password">
			  						</div>
			  						<button type="submit" class="btn btn-primary">Authenticate</button>
								</form>
							</div>
							<div id="secondItem" class="d-none">
								<form>
									<div class="form-group">
										<label class="col-form-label" for="formGroupExampleInput">Account number:</label>
										<input type="text" class="form-control" disabled="true" id="formGroupExampleInput" value="Itau" >
									</div>
			  						<button type="submit" class="btn btn-primary">Authenticate</button>
								</form>
							</div>
						</div>
						
					</div>
					<button onclick="changeSlide()" class="btn btn-primary">change</button>
				</div>
			</div>
		</div>
		<script src="/js/jquery.min.js"></script>
		<script src="/js/popper.min.js" ></script>
		<script src="/js/axios.min.js"></script>
		<script src="/js/bootstrap.min.js"></script>
		<script src="/js/angular.min.js"></script>
		<script src="/js/atm.js"></script>
		<script>
			function changeSlide(){
				if($( "#secondItem" ).hasClass( "d-none" )){
					$( "#secondItem" ).removeClass( "d-none" );
					$( "#firstItem" ).addClass( "d-none" );
				}
				else{
					$( "#secondItem" ).addClass( "d-none" );
					$( "#firstItem" ).removeClass( "d-none" );
				}
			}
		</script>
  </body>
</html>