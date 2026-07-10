package com.projeto.ufc.controller;

import com.projeto.ufc.dto.LutadorDTO;
import com.projeto.ufc.service.LutadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/lutadores")
@Tag(
     name = "Lutadores",
     description = "Operações relacionadas aos lutadores do UFC"
)
public class LutadorController {

    private final LutadorService service;

    public LutadorController(LutadorService service) {
        this.service = service;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Listar todos os lutadores")
    @GetMapping
    public ResponseEntity<Page<LutadorDTO>> listarTodos(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sort));

    return ResponseEntity.ok(
            service.listarTodos(pageable)
    );
}

    @Operation(
        summary = "Buscar lutador por ID",
        description = "Retorna um lutador pelo seu identificador"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lutador encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lutador não encontrado"
        )
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<LutadorDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }
    
    @Operation(summary = "Buscar lutadores por nome")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/buscar")
    public ResponseEntity<List<LutadorDTO>> buscarPorNome(
            @RequestParam String nome){
        
        return ResponseEntity.ok(
                service.buscarPorNome(nome)
        );
    }
    
    @Operation(summary = "Buscar lutadores por país")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<LutadorDTO>> buscarPorPais(
            @PathVariable String pais){
        
        return ResponseEntity.ok(
                service.buscarPorPais(pais)
        );
    }
    
    @Operation(summary = "Buscar lutadores acima de uma altura")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/altura")
    public ResponseEntity<List<LutadorDTO>> buscarMaisAltosQue(
            @RequestParam Double altura){
        
        return ResponseEntity.ok(
                service.buscarMaisAltosQue(altura)
        );
    }
    
    @Operation(summary = "Buscar lutadores por categoria")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/categoria")
    public ResponseEntity<List<LutadorDTO>> buscarPorCategoria(
            @RequestParam String categoria){
        
        return ResponseEntity.ok(
                service.buscarPorCategoria(categoria)
        );
    }
    
    @Operation(
            summary = "Cadastrar novo lutador",
            description = "Cria um novo lutador na base de dados"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Lutador criado com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos"
        )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LutadorDTO> salvar(
        @Valid @RequestBody LutadorDTO dto) {

        LutadorDTO novo = service.salvar(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(novo);
}
    
    @Operation(
            summary = "Atualizar lutador",
            description = "Atualiza os dados de um lutador existente"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lutador atualizado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lutador não encontrado"
        )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LutadorDTO> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody LutadorDTO dto) {

    return ResponseEntity.ok(
            service.atualizar(id, dto)
    );
}

    @Operation(
            summary = "Excluir lutador",
            description = "Remove um lutador da base de dados"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Lutador removido"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lutador não encontrado"
        )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}