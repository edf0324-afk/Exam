<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 成績参照</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			<div class="mx-3">
				<h5 class="mb-2">科目・クラス毎に参照</h5>
				<c:if test="${not empty error}">
					<div class="mb-2 text-danger">${error}</div>
				</c:if>
				<form action="TestListSubjectExecute.action" method="post">
					<div class="row g-3 mb-4">
						<div class="col-auto">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="ent_year">
								<option value="">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}">${year}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="form-label">クラス</label>
							<select class="form-select" name="class_num">
								<option value="">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}">${num}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="form-label">科目</label>
							<select class="form-select" name="subject_cd">
								<option value="">--------</option>
								<c:forEach var="sub" items="${subject_set}">
									<option value="${sub.cd}">${sub.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto align-self-end">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
				<div class="my-3 border-top"></div>
				<h5 class="mt-3 mb-2">学生毎に参照</h5>
				<c:if test="${not empty student_error}">
					<div class="mb-2 text-danger">${student_error}</div>
				</c:if>
				<form action="TestListStudentExecute.action" method="post">
					<div class="row g-3">
						<div class="col-auto">
							<label class="form-label">学生番号</label>
							<input class="form-control" type="text" name="student_no"
								value="${student_no}" placeholder="学生番号を入力してください" />
						</div>
						<div class="col-auto align-self-end">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
			</div>
		</section>
	</c:param>
</c:import>