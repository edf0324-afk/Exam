<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 学生一覧</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
			<div class="my-2 text-end px-4">
				<a href="StudentCreate.action">新規登録</a>
			</div>
			<form action="StudentList.action" method="get">
				<div class="row g-3 mx-3 mb-3">
					<div class="col-auto">
						<label class="form-label">入学年度</label>
						<select class="form-select" name="f1">
							<option value="0">---------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-auto">
						<label class="form-label">クラス</label>
						<select class="form-select" name="f2">
							<option value="0">---------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-auto align-self-end">
						<input class="form-check-input" type="checkbox" id="is-attend-check"
							name="f3" value="t" <c:if test="${not empty f3}">checked</c:if> />
						<label class="form-check-label" for="is-attend-check">在学中</label>
					</div>
					<div class="col-auto align-self-end">
						<button type="submit" class="btn btn-secondary">絞込み</button>
					</div>
				</div>
			</form>
			<c:if test="${not empty errors}">
				<div class="mx-3 text-danger">${errors.f1}</div>
			</c:if>
			<c:if test="${not empty students}">
				<div class="mx-3 mb-2">検索結果：${students.size()}件</div>
				<table class="table table-hover mx-3">
					<tr>
						<th>入学年度</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>クラス</th>
						<th>在学中</th>
						<th></th>
					</tr>
					<c:forEach var="student" items="${students}">
						<tr>
							<td>${student.entYear}</td>
							<td>${student.studentNo}</td>
							<td>${student.studentName}</td>
							<td>${student.classNum}</td>
							<td>
								<c:choose>
									<c:when test="${student.isAttend()}">○</c:when>
									<c:otherwise>×</c:otherwise>
								</c:choose>
							</td>
							<td><a href="StudentUpdate.action?no=${student.studentNo}">変更</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</section>
	</c:param>
</c:import>