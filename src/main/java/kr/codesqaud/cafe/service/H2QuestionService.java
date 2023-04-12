package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.PageHandler;
import kr.codesqaud.cafe.dto.QuestionBoardDTO;
import kr.codesqaud.cafe.dto.QuestionDetailDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.repository.H2QuestionReposotory;

@Service
@Primary
public class H2QuestionService implements QuestionService {
	private final H2QuestionReposotory repository;

	public H2QuestionService(H2QuestionReposotory repository) {
		this.repository = repository;
	}

	@Override
	public void addQuestion(QuestionWriteDTO dto) {
		repository.insert(dto);
	}

	@Override
	public QuestionBoardDTO makeQuestionBoard(int page) {
		PageHandler handler = new PageHandler(repository.countAll(), page);
		List<QuestionTitleDTO> dto = repository.selectQuestionTitlesByOffset(handler.getPostOffset(),
			handler.getPageSize());
		return new QuestionBoardDTO(handler, dto);
	}

	@Override
	public QuestionDetailDTO findQuestion(int idx) throws NoSuchElementException {
		return repository.selectByIdx(idx);
	}
}
