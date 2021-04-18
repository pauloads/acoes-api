package com.paulocorrea.desafiopagseguro.batch;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader reader() {
        return new FlatFileItemReaderBuilder<AcaoEntity>().name("acoesReader")
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
    public MongoItemWriter<AcaoEntity> writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AcaoEntity>().template(mongoTemplate).collection("acoes")
                .build();
    }

    @Bean
    public AcaoItemProcessor processor() {
        return new AcaoItemProcessor();
    }

    @Bean
    public Step importarRegistros(FlatFileItemReader<AcaoEntity> itemReader, MongoItemWriter<AcaoEntity> mongoItemWriter) {
        return this.stepBuilderFactory.get("importarRegistros").<AcaoEntity, AcaoEntity>chunk(5)
                .reader(itemReader)
                .processor(processor())
                .writer(mongoItemWriter)
                .build();
    }

    @Bean
    public Job importarAcaoJob(JobCompletionNotificationListener listener, Step importarRegistros) {
        return this.jobBuilderFactory.get("importarAcaoJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(importarRegistros).build();
    }
}
