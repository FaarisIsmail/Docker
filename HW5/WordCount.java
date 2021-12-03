import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

    public static class WordCountMapper
        extends Mapper<Object, Text, Text, IntWritable> {


            private HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
            private TreeMap<Integer, String> sortedCount = new TreeMap<Integer, String>();
            private TreeMap<Integer, String> top5 = new TreeMap<Integer, String>();
            private ArrayList<String> stopWords = new ArrayList<String>(Arrays.asList("he", "she", "they", "the", "a", "an", "are", "you", "of", "is", "and", "or"));
            private Text word = new Text();

            public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
                    StringTokenizer itr = new StringTokenizer(value.toString());
                    //Iterate through words. If it is already found in the wordCount Hashmap, increase count by 1. Otherwise, initialize count to 1
                    while(itr.hasMoreTokens()) {
			            String token = itr.nextToken();
                        if(!stopWords.contains(token)){
                            if(wordCount.containsKey(token)) {
                                wordCount.put(token, wordCount.get(token) + 1);
                            }
                            else {
                                wordCount.put(token, 1);
                            }
                        }
                    }

                    //Add all wordCount keys into Treemap so it can be sorted
                    for(String s: wordCount.keySet()) {
                        sortedCount.put(wordCount.get(s), s);
                    }
                    //Only add the top 5 (last 5) words in the tree map to top5
                    for(int i=0; i < 5; i++) {
                        if (sortedCount.size() == 0)
                            break;
                        top5.put(sortedCount.lastKey(), sortedCount.get(sortedCount.lastKey()));
                        sortedCount.remove(sortedCount.lastKey());
                    }
            }


            protected void cleanup(Context context) throws IOException, InterruptedException {
                //Write out top5 words
               for(Map.Entry<Integer, String> entry: top5.entrySet()) {
                    context.write(new Text(entry.getValue()), new IntWritable(entry.getKey()));

               }
            }

        }

        public static class WordCountReducer extends Reducer<Text, IntWritable, IntWritable, Text> {
            
            private TreeMap<Integer, String> finalSorted = new TreeMap<Integer, String>();
            private TreeMap<Integer, String> finalTop5 = new TreeMap<Integer, String>();

            public void reduce(Text key, Iterable<IntWritable> values, Context context)
            {
                int sum = 0;

                for(IntWritable value : values) {
                    sum += value.get();
                }

                finalSorted.put(sum, key.toString());

                for(int i=0; i < 5; i++) {
                    if (finalSorted.size() == 0)
                        break;
                    finalTop5.put(finalSorted.lastKey(), finalSorted.get(finalSorted.lastKey()));
                    finalSorted.remove(finalSorted.lastKey());
                }
            }

            public void cleanup(Context context) throws IOException, InterruptedException{
                for (Map.Entry<Integer, String> entry : finalTop5.entrySet()) {
                    context.write(new IntWritable(Integer.parseInt(entry.getValue())), new Text(entry.getKey().toString()));
                }
            }
	}

    public static void main(String[] args) throws Exception {
        if(args.length != 2) {
            System.err.println("Usage: WordCount <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setNumReduceTasks(1);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}