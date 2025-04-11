import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import java.io.File;
import java.util.List;

/**
 * A personalized recommendation system using Apache Mahout to suggest products
 * based on user preferences.
 *
 * This program reads user-item preference data from a CSV file,
 * processes it using an item-based recommendation approach,
 * and suggests items based on similarity scores.
 */
public class RecommendationSystem {
    public static void main(String[] args) {
        try {
            // Load data from a CSV file (userId, itemId, preference)
            File file = new File("data.csv");
            DataModel model = new FileDataModel(file);

            // Define item similarity algorithm
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Create an item-based recommender
            Recommender recommender = new GenericItemBasedRecommender(model, similarity);

            // Generate recommendations for a given user (e.g., user ID 1)
            int userId = 1;
            int numRecommendations = 5;
            List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

            // Display recommendations with a user-friendly message
            System.out.println("Hello, User " + userId + "! Based on your preferences, we recommend:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("- Item ID: " + recommendation.getItemID() + " (Score: " + recommendation.getValue() + ")");
            }
        } catch (Exception e) {
            System.err.println("Oops! Something went wrong: " + e.getMessage());
        }
    }
  }
