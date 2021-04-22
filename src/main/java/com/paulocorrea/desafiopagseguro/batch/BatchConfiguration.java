package com.paulocorrea.desafiopagseguro.batch;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Bean
    public FlatFileItemReader fileReader() {
        return new FlatFileItemReaderBuilder<AcaoEntity>().name("fileReader")
                .resource(new PathResource("acoes.txt"))
                .delimited()
                .delimiter(";")
                .names(new String[]{"codigo", "nome", "quantidadeTeorica", "percentualDeParticipacao"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<AcaoEntity>() {{
                    setTargetType(AcaoEntity.class);
                }})
                .build();
    }

    @Bean
    public MongoItemWriter<AcaoEntity> mongoItemWriter() {
        return new MongoItemWriterBuilder<AcaoEntity>().template(mongoTemplate).collection("acoes")
                .build();
    }

    @Bean
    public AcaoItemProcessor processor() {
        return new AcaoItemProcessor();
    }

    @Bean
    public ObterValorDeMercadoItemProcessor obterValorDeMercadoItemProcessor() {
        return new ObterValorDeMercadoItemProcessor();
    }

    @Bean
    public Step importarRegistros() {
        return this.stepBuilderFactory.get("importarRegistros")
                .<AcaoEntity, AcaoEntity>chunk(5)
                .reader(fileReader())
                .processor(processor())
                .writer(mongoItemWriter())
                .build();
    }

    @Bean
    public Step setarValorDeMercadoDasDezMaioresAcoes() {
        return this.stepBuilderFactory.get("setarValorDeMercadoDasDezMaioresAcoes")
                .<AcaoEntity, AcaoEntity>chunk(10)
                .reader(obterDezMaioresAcoesReader())
                .processor(obterValorDeMercadoItemProcessor())
                .writer(mongoItemWriter())
                .build();
    }

    @Bean
    public MongoItemReader<AcaoEntity> obterDezMaioresAcoesReader() {
        MongoItemReader<AcaoEntity> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("quantidadeTeorica", Sort.Direction.ASC);
        }});
        reader.setMaxItemCount(10);
        reader.setTargetType(AcaoEntity.class);
        reader.setQuery("{}");
        return reader;
    }


    @Bean
    public Job importarAcaoJob(JobCompletionNotificationListener listener) {
        return this.jobBuilderFactory.get("importarAcaoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(importarRegistros())
                .next(setarValorDeMercadoDasDezMaioresAcoes())
                .build();
    }
}
