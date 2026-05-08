<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 科目別成績一覧</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目別成績一覧</h2>
			<div class="mx-3">
				<%-- 科目・クラス毎に参照フォーム --%>
				<h5 class="mb-2">科目・クラス毎に参照</h5>
				<form action="TestListSubjectExecute.action" method="post">
					<div class="row g-3 mb-4">
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
						<div class="col-auto align-self-end">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
				<div class="my-3 border-top"></div>
				<%-- 学生毎に参照フォーム --%>
				<h5 class="mt-3 mb-2">学生毎に参照</h5>
				<form action="TestListStudentExecute.action" method="post">
					<div class="row g-3 mb-4">
						<div class="col-auto">
							<label class="form-label">学生番号</label>
							<input class="form-control" type="text" name="student_no"
								placeholder="学生番号を入力してください" />
						</div>
						<div class="col-auto align-self-end">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
				<%-- 検索結果 --%>
				<p>科目：${subject.name}</p>
				<table class="table table-bordered w-75 mb-3">
					<tr>
						<th>入学年度</th>
						<td>${ent_year}</td>
						<th>クラス</th>
						<td>${class_num}</td>
						<th>科目</th>
						<td>${subject.name}</td>
					</tr>
				</table>
				<table class="table table-hover table-bordered">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<c:forEach begin="1" end="${max_no}" var="n">
							<th>${n}回</th>
						</c:forEach>
					</tr>
					<c:forEach var="t" items="${test_list}">
						<tr>
							<td>${t.entYear}</td>
							<td>${t.classNum}</td>
							<td>${t.studentNo}</td>
							<td>${t.studentName}</td>
							<c:forEach begin="1" end="${max_no}" var="n">
								<td>${t.getPoint(n)}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
				<a href="TestList.action" class="btn btn-secondary mt-2">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>