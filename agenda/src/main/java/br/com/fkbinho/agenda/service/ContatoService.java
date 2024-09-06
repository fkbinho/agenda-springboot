package br.com.fkbinho.agenda.service;

import br.com.fkbinho.agenda.DTO.ContatoDto;
import br.com.fkbinho.agenda.exceptions.IllegalArgumentException;
import br.com.fkbinho.agenda.exceptions.ObjectNotFoundException;
import br.com.fkbinho.agenda.models.Contato;
import br.com.fkbinho.agenda.repository.ContatoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Contato findById(Long id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        if (contato.isPresent()) {
            return contato.get();
        }
        throw new ObjectNotFoundException("Contato não encontrado.");
    }

    public List<Contato> findByName(String nome) {
        return contatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Contato> findAll() {
        return contatoRepository.findAll();
    }

    public Contato save(ContatoDto contatoDto) {
        verificarEmailDuplicado(contatoDto);
        contatoDto.setId(null);
        return contatoRepository.save(modelMapper.map(contatoDto, Contato.class));
    }

    public Contato update(ContatoDto contatoDto) {
        findById(contatoDto.getId());
        verificarEmailDuplicado(contatoDto);
        return contatoRepository.save(modelMapper.map(contatoDto, Contato.class));
    }

    public void delete(Long id) {
        findById(id);
        contatoRepository.deleteById(id);
    }

    private void verificarEmailDuplicado(ContatoDto contatoDto) {
        Optional<Contato> contato = contatoRepository.findByEmail(contatoDto.getEmail());
        if(contato.isPresent() && contato.get().getEmail().equalsIgnoreCase(contatoDto.getEmail())){
            throw new IllegalArgumentException("Já existe um contato com o e-mail " + contatoDto.getEmail());
        }
    }
}