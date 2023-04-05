package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.PageHandler;
import kr.codesqaud.cafe.dto.QuestionBoardDTO;
import kr.codesqaud.cafe.dto.QuestionDetailsDTO;
import kr.codesqaud.cafe.dto.QuestionTitleDTO;
import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.repository.QuestionRepository;

@Service
public class QuestionService {

	private final QuestionRepository repository;

	@Autowired
	public QuestionService(QuestionRepository repository) {
		this.repository = repository;
	}

	public void addQuestion(QuestionWriteDTO dto) {
		repository.insert(dto);
	}

	public QuestionBoardDTO makeQuestionBoard(int page) {
		PageHandler handler = new PageHandler(repository.countAll(), page);
		List<QuestionTitleDTO> dto = repository.selectQuestionTitlesByOffset(handler.getPostOffset(),
			handler.getPageSize());
		return new QuestionBoardDTO(handler, dto);
	}

	public QuestionDetailsDTO findQuestion(int idx) throws NoSuchElementException {
		return repository.selectByIdx(idx);
	}
}
