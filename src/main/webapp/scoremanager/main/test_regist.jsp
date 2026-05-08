<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 成績登録</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>
			<div class="mx-3">
				<c:if test="${not empty error}">
					<div class="mb-3 text-danger">${error}</div>
				</c:if>
				<form action="TestRegistExecute.action" method="post" id="searchForm">
					<div class="row g-3 mb-3">
						<div class="col-auto">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="ent_year">
								<option value="">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="form-label">クラス</label>
							<select class="form-select" name="class_num">
								<option value="">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="form-label">科目</label>
							<select class="form-select" name="subject_cd">
								<option value="">--------</option>
								<c:forEach var="sub" items="${subject_set}">
									<option value="${sub.cd}" <c:if test="${sub.cd == subject.cd}">selected</c:if>>${sub.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto">
							<label class="form-label">回数</label>
							<select class="form-select" name="no">
								<option value="">--------</option>
								<c:forEach var="n" items="${no_set}">
									<option value="${n}" <c:if test="${n == no}">selected</c:if>>${n}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-auto align-self-end">
							<button type="button" class="btn btn-secondary"
								onclick="document.getElementById('searchForm').action='TestRegist.action'; document.getElementById('searchForm').submit();">絞込み</button>
						</div>
					</div>
					<c:if test="${not empty students}">
						<p>科目：${subject.name}（${no}回）</p>
						<table class="table table-hover">
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>点数</th>
							</tr>
							<c:forEach var="student" items="${students}">
								<tr>
									<td>${student.entYear}</td>
									<td>${student.classNum}</td>
									<td>${student.studentNo}</td>
									<td>${student.studentName}</td>
									<td>
										<input class="form-control" style="width:100px" type="number"
											name="point_${student.studentNo}" min="0" max="100"
											value="${existing_points[student.studentNo]}"
											placeholder="0〜100" />
									</td>
								</tr>
							</c:forEach>
						</table>
						<div class="mt-3">
							<button type="submit" class="btn btn-primary">登録して終了</button>
						</div>
					</c:if>
				</form>
			</div>
		</section>
	</c:param>
</c:import>