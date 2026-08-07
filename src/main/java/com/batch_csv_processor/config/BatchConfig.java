package com.batch_csv_processor.config;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.entity.Product;
import com.batch_csv_processor.exception.InvalidProductException;
import com.batch_csv_processor.listner.ProductJobExecutionListener;
import com.batch_csv_processor.listner.ProductSkipListener;
import com.batch_csv_processor.listner.ProductStepExecutionListener;
import com.batch_csv_processor.processor.ProductProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//public class BatchConfig {
//
//    @Bean
//    public FlatFileItemReader<ProductCsv> productReader() {
//
//        FlatFileItemReader<ProductCsv> reader = new FlatFileItemReader<>();
//        /*
//        This reader reads one record at a time.
//        It does not load the entire CSV into memory.
//        That's one of the reasons Spring Batch can process millions of records.
//         */
//
//        reader.setName("productCsvReader");
//        /*
//        This name is used internally by Spring Batch for: restartability, metadata, debugging
//         */
//
//        // tells Spring where the file is.
//        reader.setResource(new ClassPathResource("product_details.csv"));
//        // for skip the header that is line one
//        reader.setLinesToSkip(1);
//
//        // creates java object for each row by CSV read -> split columns -> Map Columns -> java object
//        DefaultLineMapper<ProductCsv> lineMapper = new DefaultLineMapper<>();
//
//        // mapper need this object for -> Responsible for splitting
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//
//
//        // Comma-separated values.
//        tokenizer.setDelimiter(",");
//        // These must exactly match the field names in DTO that is ProductCsv
//        tokenizer.setNames(
//                "id",
//                "name",
//                "price",
//                "quantity",
//                "discount"
//        );
//
//        // BeanWrapperFieldSetMapper wrapper class provide by spring, shorthand
//        BeanWrapperFieldSetMapper<ProductCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//
//        // This is responsible for creating and calling setters automatically for ProductCsv csv = new ProductCsv();.
//        fieldSetMapper.setTargetType(ProductCsv.class);
//
//
//        // Connect Everything
//        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        reader.setLineMapper(lineMapper);
//
//        return reader;
//    }
//
//    @Bean
//    public JpaItemWriter<Product> productWriter(EntityManagerFactory entityManagerFactory) {
//
//        JpaItemWriter<Product> writer = new JpaItemWriter<>();
//
//        writer.setEntityManagerFactory(entityManagerFactory);
//
//        return writer;
//    }
//
//    @Bean
//    public Step productImportStep(JobRepository jobRepository,
//                                  PlatformTransactionManager transactionManager,
//                                  FlatFileItemReader<ProductCsv> reader,
//                                  ProductProcessor processor,
//                                  JpaItemWriter<Product> writer) {
//
//        return new StepBuilder("productImportStep", jobRepository)
//
//                .<ProductCsv, Product>chunk(1000, transactionManager)
//
//                .reader(reader)
//
//                .processor(processor)
//
//                .writer(writer)
//
//                .build();
//    }
//
//    @Bean
//    public Job importProductJob(JobRepository jobRepository,
//                                Step productImportStep,
//                                ProductJobExecutionListener listener) {
//
//        return new JobBuilder("importProductJob", jobRepository)
//                .listener(listener)
//                .start(productImportStep)
//                .build();
//    }
//
//}

@Configuration
public class BatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<ProductCsv> productReader(
            @Value("#{jobParameters['file']}") String filePath) {

        FlatFileItemReader<ProductCsv> reader = new FlatFileItemReader<>();

        reader.setName("productCsvReader");
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);

        DefaultLineMapper<ProductCsv> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                "id",
                "name",
                "price",
                "quantity",
                "discount"
        );

        BeanWrapperFieldSetMapper<ProductCsv> fieldSetMapper =
                new BeanWrapperFieldSetMapper<>();

        fieldSetMapper.setTargetType(ProductCsv.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JpaItemWriter<Product> productWriter(EntityManagerFactory entityManagerFactory) {

        JpaItemWriter<Product> writer = new JpaItemWriter<>();

        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
    }

    @Bean
    public Step productImportStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  FlatFileItemReader<ProductCsv> reader,
                                  ProductProcessor processor,
                                  JpaItemWriter<Product> writer,
                                  ProductStepExecutionListener listener,
                                  ProductSkipListener skipListener) {

        return new StepBuilder("productImportStep", jobRepository)

                .<ProductCsv, Product>chunk(1000, transactionManager)

                .reader(reader)

                .processor(processor)

                .writer(writer)

                .listener(listener)

                .listener(skipListener)

                .faultTolerant()

                .skip(InvalidProductException.class)

                .skipLimit(100)

                .retry(TransientDataAccessException.class)

                .retryLimit(3)

                .build();
    }

    @Bean
    public Job importProductJob(JobRepository jobRepository,
                                Step productImportStep,
                                ProductJobExecutionListener listener) {

        return new JobBuilder("importProductJob", jobRepository)
                .listener(listener)
                .start(productImportStep)
                .build();
    }

}
