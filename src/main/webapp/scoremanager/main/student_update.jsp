<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 学生変更</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form action="StudentUpdateExecute.action" method="post">
				<div class="mx-3">
					<%-- 学生番号は変更不可のため hidden で送信 --%>
					<input type="hidden" name="no" value="${student.studentNo}" />

					<div class="mb-3">
						<label class="form-label">学生番号</label>
						<input class="form-control" type="text" value="${student.studentNo}" disabled />
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-name-input">氏名</label>
						<input class="form-control" type="text" id="student-name-input" name="name"
							value="${student.studentName}" maxlength="10" required />
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-ent-year-select">入学年度</label>
						<select class="form-select" id="student-ent-year-select" name="ent_year" required>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == student.entYear}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mb-3">
						<label class="form-label" for="student-class-num-select">クラス</label>
						<select class="form-select" id="student-class-num-select" name="class_num" required>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mb-3 form-check">
						<input class="form-check-input" type="checkbox" id="student-is-attend-check"
							name="is_attend" value="t" <c:if test="${student.isAttend()}">checked</c:if> />
						<label class="form-check-label" for="student-is-attend-check">在学中</label>
					</div>
					<div class="mt-4">
						<button type="submit" class="btn btn-primary">変更して戻る</button>
					</div>
				</div>
			</form>
			<div class="mt-3 px-3">
				<a href="StudentList.action">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>