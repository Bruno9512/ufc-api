package com.projeto.ufc.service;

import com.projeto.ufc.dto.LutadorDTO;
import com.projeto.ufc.exception.LutadorNotFoundException;
import com.projeto.ufc.model.Lutador;
import com.projeto.ufc.repository.LutadorRepository;
import com.projeto.ufc.mapper.LutadorMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LutadorService {

    private static final Logger logger =
            LoggerFactory.getLogger(LutadorService.class);

    private final LutadorRepository repository;

    public LutadorService(LutadorRepository repository) {
        this.repository = repository;
    }

    public Page<LutadorDTO> listarTodos(Pageable pageable) {

        logger.info("Listando lutadores.");

        return repository.findAll(pageable)
                .map(LutadorMapper::toDTO);
    }

    public LutadorDTO buscarPorId(Long id) {

        logger.info("Buscando lutador pelo ID {}", id);

        return LutadorMapper.toDTO(
                buscarLutadorPorId(id)
        );
    }

    public List<LutadorDTO> buscarPorNome(String nome) {

        logger.info("Buscando lutadores pelo nome {}", nome);

        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(LutadorMapper::toDTO)
                .toList();
    }

    public List<LutadorDTO> buscarPorPais(String pais) {

        logger.info("Buscando lutadores do país {}", pais);

        return repository.findByPaisIgnoreCase(pais)
                .stream()
                .map(LutadorMapper::toDTO)
                .toList();
    }

    public List<LutadorDTO> buscarMaisAltosQue(Double altura) {

        logger.info("Buscando lutadores acima de {}m", altura);

        return repository.buscarMaisAltosQue(altura)
                .stream()
                .map(LutadorMapper::toDTO)
                .toList();
    }

    public List<LutadorDTO> buscarPorCategoria(String categoria) {

        logger.info("Buscando categoria {}", categoria);

        return repository.findByCategoriaIgnoreCase(categoria)
                .stream()
                .map(LutadorMapper::toDTO)
                .toList();
    }

    public LutadorDTO salvar(LutadorDTO dto) {

        logger.info("Cadastrando lutador {}", dto.getNome());

        Lutador salvo =
                repository.save(LutadorMapper.toEntity(dto));

        return LutadorMapper.toDTO(salvo);
    }

    public LutadorDTO atualizar(Long id, LutadorDTO dto) {

        logger.info("Atualizando lutador {}", id);

        Lutador lutador = buscarLutadorPorId(id);

        lutador.setNome(dto.getNome());
        lutador.setPais(dto.getPais());
        lutador.setCategoria(dto.getCategoria());
        lutador.setAltura(dto.getAltura());

        return LutadorMapper.toDTO(
                repository.save(lutador)
        );
    }

    public void excluir(Long id) {

        logger.info("Excluindo lutador {}", id);

        repository.delete(buscarLutadorPorId(id));
    }

    private Lutador buscarLutadorPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new LutadorNotFoundException(
                                "Lutador com ID " + id + " não encontrado"));
    }

    
}