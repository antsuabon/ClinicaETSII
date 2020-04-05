<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<%@ attribute name="name" required="true" rtexprvalue="true" description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">

	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/" title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Anónimo <span
						class="glyphicon glyphicon-chevron-down"></span>
				</a>

					<ul class="dropdown-menu">

						<petclinic:menuItem active="${name eq 'doctors'}" url="/anonymous/doctors" title="list doctors">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							<span>Médicos</span>
						</petclinic:menuItem>

						<petclinic:menuItem active="${name eq 'medicines'}" url="/anonymous/medicines" title="list medicines">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							<span>Medicamentos</span>
						</petclinic:menuItem>

					</ul></li>




				<sec:authorize access="hasAuthority('patient')">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Paciente <span
							class="glyphicon glyphicon-chevron-down"></span>

					</a>

						<ul class="dropdown-menu">


							<petclinic:menuItem active="${name eq 'patient appointments'}" url="/patient/appointments" title="list my appointments">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Mis Citas</span>
							</petclinic:menuItem>

							<petclinic:menuItem active="${name eq 'appointments'}" url="/patient/appointments/table" title="request appointment">
								<span aria-hidden="true"></span>
								<span>Solicitar cita médica</span>
							</petclinic:menuItem>


							<petclinic:menuItem active="${name eq 'prescriptions'}" url="/patient/prescriptions" title="list my prescriptions">
								<span aria-hidden="true"></span>
								<span>Mis prescripciones</span>
							</petclinic:menuItem>

						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasAuthority('doctor')">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Médico <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>

						<ul class="dropdown-menu">

							<petclinic:menuItem active="${name eq 'doctor patients'}" url="/doctor/patients" title="list my patients">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Mis Pacientes</span>
							</petclinic:menuItem>

							<petclinic:menuItem active="${name eq 'doctor appointments'}" url="/doctor/appointments" title="list my patients">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Citas Pendientes</span>
							</petclinic:menuItem>

						</ul></li>
				</sec:authorize>


				<sec:authorize access="hasAuthority('administrative')">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Administrativo <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>

						<ul class="dropdown-menu">

							<petclinic:menuItem active="${name eq 'administrative new patient'}" url="/administrative/patients/new" title="add new patient">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Añadir Paciente</span>
							</petclinic:menuItem>

							<petclinic:menuItem active="${name eq 'administrative patients'}" url="/administrative/patients" title="list my patients">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Listar Pacientes</span>
							</petclinic:menuItem>
							
							<petclinic:menuItem active="${name eq 'administrative doctors'}" url="/administrative/doctors" title="list my doctors">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Listar Médicos</span>
							</petclinic:menuItem>

						</ul></li>
				</sec:authorize>
				
				
				<sec:authorize access="hasAuthority('admin')">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> Administrador <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>

						<ul class="dropdown-menu">

							<petclinic:menuItem active="${name eq 'admin list patient'}" url="/admin/patients" title="list patient">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Pacientes del centro</span>
							</petclinic:menuItem>


						</ul></li>
						
				</sec:authorize>

				<petclinic:menuItem active="${name eq 'error'}" url="/oups" title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>


			</ul>


			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar sesión</a></li>
					<!-- <li><a href="<c:url value="/users/new" />">Register</a></li> -->
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span
							class="glyphicon glyphicon-user"></span> <strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />" class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

							<sec:authorize access="hasAuthority('doctor')">
								<petclinic:menuItem active="${name eq 'doctor profile'}" url="/doctor" title="show doctor profile">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
									<span>Mi perfil</span>
								</petclinic:menuItem>
							</sec:authorize>

							<sec:authorize access="hasAuthority('patient')">
								<petclinic:menuItem active="${name eq 'patient profile'}" url="/patient" title="show patient profile">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
									<span>Mi perfil</span>
								</petclinic:menuItem>
							</sec:authorize>

							<!--
                            <li>
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
