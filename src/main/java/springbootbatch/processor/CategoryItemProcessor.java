package springbootbatch.processor;

import org.springframework.batch.item.ItemProcessor;
import springbootbatch.model.Category;

/**
 * Created by Lightning on 8/6/2015.
 */
public class CategoryItemProcessor implements ItemProcessor<Category, Category> {

    @Override
    public Category process(Category category) throws Exception {
        return category;
    }
}
