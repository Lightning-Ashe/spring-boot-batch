package springbootbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import springbootbatch.model.Category;
import springbootbatch.processor.CategoryItemProcessor;

/**
 * Created by Lightning on 8/4/2015.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    DatabaseConfiguration databaseConfiguration;

    // tag::readerwriterprocessor[]
    @Bean(destroyMethod = "")
    public ItemReader<Category> reader() {
        JpaPagingItemReader<Category> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(databaseConfiguration.entityManagerFactory().getObject());
        reader.setQueryString("from tbl_category_code");

        return reader;
    }

    @Bean
    public ItemProcessor<Category, Category> processor() {
        return new CategoryItemProcessor();
    }

    @Bean
    public ItemWriter<Category> writer() {
        FlatFileItemWriter<Category> writer = new FlatFileItemWriter<Category>();
        writer.setResource(new FileSystemResource("C:\\sources\\category.txt"));
        DelimitedLineAggregator<Category> delLineAgg = new DelimitedLineAggregator<Category>();
        delLineAgg.setDelimiter(",");
        BeanWrapperFieldExtractor<Category> fieldExtractor = new BeanWrapperFieldExtractor<Category>();
        fieldExtractor.setNames(new String[] {"categoryCode", "categoryName", "seq", "note"});
        delLineAgg.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(delLineAgg);
        return writer;

    }
    // end::readerwriterprocessor[]


    @Bean
    public Job cateogyrJob(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("cateogyrJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Category> reader,
                      ItemWriter<Category> writer, ItemProcessor<Category, Category> processor) {
        return stepBuilderFactory.get("step1")
                .<Category, Category> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
