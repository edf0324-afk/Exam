<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 学生別成績一覧</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生別成績一覧</h2>
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
				<%-- 学生毎に参照フォーム --%>
				<h5 class="mt-3 mb-2">学生毎に参照</h5>
				<form action="TestListStudentExecute.action" method="post">
					<div class="row g-3 mb-4">
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
				<%-- 検索結果 --%>
				<c:if test="${not empty student}">
					<p>氏名：${student.studentName}（${student.studentNo}）</p>
					<table class="table table-hover table-bordered">
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>得点</th>
						</tr>
						<c:forEach var="t" items="${test_list_student}">
							<tr>
								<td>${t.subjectName}</td>
								<td>${t.subjectCd}</td>
								<td>${t.num}回</td>
								<td>${t.point}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<a href="TestList.action" class="btn btn-secondary mt-2">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>