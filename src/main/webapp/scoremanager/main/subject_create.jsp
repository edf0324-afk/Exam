<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 科目登録</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			<form action="SubjectCreateExecute.action" method="post">
				<div class="mx-3">
					<div class="mb-3">
						<label class="form-label" for="subject-cd-input">科目コード</label>
						<input class="form-control ${not empty cdError ? 'is-invalid' : ''}"
							type="text" id="subject-cd-input" name="cd"
							value="${cd}" placeholder="科目コードを入力してください" maxlength="10" required />
						<c:if test="${not empty cdError}">
							<div class="invalid-feedback">${cdError}</div>
						</c:if>
					</div>
					<div class="mb-3">
						<label class="form-label" for="subject-name-input">科目名</label>
						<input class="form-control" type="text" id="subject-name-input" name="name"
							value="${name}" placeholder="科目名を入力してください" maxlength="20" required />
					</div>
					<div class="mt-4">
						<button type="submit" class="btn btn-primary">登録する</button>
					</div>
				</div>
			</form>
			<div class="mt-3 px-3">
				<a href="SubjectList.action">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>