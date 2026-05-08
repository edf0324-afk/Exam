<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム - 科目登録完了</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			<div class="mx-3">
				<%-- ②緑のアラート --%>
				<div class="alert alert-success">登録が完了しました</div>
				<%-- ③戻る ④科目一覧 --%>
				<a href="SubjectCreate.action" class="btn btn-link">戻る</a>
				<a href="SubjectList.action" class="btn btn-link">科目一覧</a>
			</div>
		</section>
	</c:param>
</c:import>