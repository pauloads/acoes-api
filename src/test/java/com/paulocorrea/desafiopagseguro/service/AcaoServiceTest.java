package com.paulocorrea.desafiopagseguro.service;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import com.paulocorrea.desafiopagseguro.exception.AcaoNaoEncontradaException;
import com.paulocorrea.desafiopagseguro.repository.AcaoMongoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AcaoServiceTest {

    @Mock
    private AcaoMongoRepository acaoMongoRepository;

    @InjectMocks
    private AcaoService acaoService;

    @Test(expected = AcaoNaoEncontradaException.class)
    public void deveLancarExcecaoQuandoNaoEncontrarAcaoPorCodigo() {
        Mockito.when(acaoMongoRepository.findByCodigo(any())).thenReturn(Optional.empty());
        acaoService.obterAcaoPorCodigo("1234");
    }

    @Test(expected = AcaoNaoEncontradaException.class)
    public void deveLancarExcecaoQuandoNaoEncontrarAcaoPorNome() {
        Mockito.when(acaoMongoRepository.findByNome(any())).thenReturn(Optional.empty());
        acaoService.obterAcaoPorNome("1234");
    }

    @Test
    public void deveBuscarAcaoPorNome() {
        AcaoEntity acaoEntity = mock(AcaoEntity.class);
        Mockito.when(acaoMongoRepository.findByNome(any())).thenReturn(Optional.of(acaoEntity));
        AcaoGetDTO acaoGetDTO = acaoService.obterAcaoPorNome("123");
        assertNotNull(acaoGetDTO);
        verify(acaoMongoRepository, times(1)).findByNome(any());
    }

    @Test
    public void deveBuscarAcaoPorCodigo() {
        AcaoEntity acaoEntity = mock(AcaoEntity.class);
        Mockito.when(acaoMongoRepository.findByCodigo(any())).thenReturn(Optional.of(acaoEntity));
        AcaoGetDTO acaoGetDTO = acaoService.obterAcaoPorCodigo("123");
        assertNotNull(acaoGetDTO);
        verify(acaoMongoRepository, times(1)).findByCodigo(any());
    }

    @Test
    public void deveListarAcoes() {
        acaoService.listarAcoes();
        verify(acaoMongoRepository, times(1)).findAll();
    }

    @Test
    public void deveRemoverAcao() {
        AcaoEntity acaoEntity = mock(AcaoEntity.class);
        when(acaoMongoRepository.findById(anyString())).thenReturn(Optional.of(acaoEntity));
        acaoService.removerAcao("123");
        verify(acaoMongoRepository, times(1)).delete(any(AcaoEntity.class));
    }

    @Test(expected = AcaoNaoEncontradaException.class)
    public void deveLancarExcecaoQuandoTentarRemoverUmaAcaoInexistente() {
        Mockito.when(acaoMongoRepository.findById(anyString())).thenReturn(Optional.empty());
        acaoService.removerAcao("1234");
    }

}