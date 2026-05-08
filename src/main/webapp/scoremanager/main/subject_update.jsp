<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 科目変更</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="post">
				<div class="mx-3">
					<%-- 科目コードは変更不可のため hidden で送信 --%>
					<input type="hidden" name="cd" value="${subject.cd}" />

					<div class="mb-3">
						<label class="form-label">科目コード</label>
						<input class="form-control" type="text" value="${subject.cd}" disabled />
					</div>
					<div class="mb-3">
						<label class="form-label" for="subject-name-input">科目名</label>
						<input class="form-control" type="text" id="subject-name-input" name="name"
							value="${subject.name}" maxlength="20" required />
					</div>
					<div class="mt-4">
						<button type="submit" class="btn btn-primary">変更して戻る</button>
					</div>
				</div>
			</form>
			<div class="mt-3 px-3">
				<a href="SubjectList.action">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>