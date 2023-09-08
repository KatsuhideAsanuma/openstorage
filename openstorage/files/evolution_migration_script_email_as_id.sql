-- # --- !Ups

CREATE TABLE `image_metadata` (
  FOREIGN KEY (`metadata_id`) REFERENCES `metadata`(`id`),
  FOREIGN KEY (`image_id`) REFERENCES `image_table`(`id`),
  `image_id` int,
  `metadata_id` int
);

CREATE TABLE `image_table` (
  `id` int,
  `file_path` varchar,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `image_tags` (
  FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`),
  FOREIGN KEY (`image_id`) REFERENCES `image_table`(`id`),
  `image_id` int,
  `tag_id` int
);

CREATE TABLE `metadata` (
  `id` int,
  `key_name` varchar,
  `value` text
);

CREATE TABLE `music_metadata` (
  FOREIGN KEY (`metadata_id`) REFERENCES `metadata`(`id`),
  FOREIGN KEY (`music_id`) REFERENCES `music_table`(`id`),
  `music_id` int,
  `metadata_id` int
);

CREATE TABLE `music_table` (
  `id` int,
  `file_path` varchar,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `music_tags` (
  FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`),
  FOREIGN KEY (`music_id`) REFERENCES `music_table`(`id`),
  `music_id` int,
  `tag_id` int
);

CREATE TABLE `tags` (
  `id` int,
  `tag_name` varchar
);

CREATE TABLE `text_metadata` (
  FOREIGN KEY (`metadata_id`) REFERENCES `metadata`(`id`),
  FOREIGN KEY (`text_id`) REFERENCES `text_table`(`id`),
  `text_id` int,
  `metadata_id` int
);

CREATE TABLE `text_table` (
  `id` int,
  `content` text,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `text_tags` (
  FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`),
  FOREIGN KEY (`text_id`) REFERENCES `text_table`(`id`),
  `text_id` int,
  `tag_id` int
);

CREATE TABLE `video_metadata` (
  FOREIGN KEY (`metadata_id`) REFERENCES `metadata`(`id`),
  FOREIGN KEY (`video_id`) REFERENCES `video_table`(`id`),
  `video_id` int,
  `metadata_id` int
);

CREATE TABLE `video_table` (
  `id` int,
  `file_path` varchar,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `video_tags` (
  FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`),
  FOREIGN KEY (`video_id`) REFERENCES `video_table`(`id`),
  `video_id` int,
  `tag_id` int
);

CREATE TABLE `auth_table` (
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  FOREIGN KEY (`email`) REFERENCES `users_table`(`email`)
);

CREATE TABLE `users_table` (
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL PRIMARY KEY,
  `api_key` VARCHAR(255) NOT NULL,
  `user_type` VARCHAR(255) NOT NULL
);

CREATE TABLE `api_table` (
  `api_key` varchar
);

CREATE TABLE `streaming_table` (
  `tag` varchar,
  `metadata_value` varchar
);

CREATE TABLE `metadata_table` (
  `tag` varchar,
  `metadata_value` varchar
);

-- # --- !Downs

DROP TABLE `image_metadata`;
DROP TABLE `image_table`;
DROP TABLE `image_tags`;
DROP TABLE `metadata`;
DROP TABLE `music_metadata`;
DROP TABLE `music_table`;
DROP TABLE `music_tags`;
DROP TABLE `tags`;
DROP TABLE `text_metadata`;
DROP TABLE `text_table`;
DROP TABLE `text_tags`;
DROP TABLE `video_metadata`;
DROP TABLE `video_table`;
DROP TABLE `video_tags`;
DROP TABLE `auth_table`;
DROP TABLE `users_table`;
DROP TABLE `api_table`;
DROP TABLE `streaming_table`;
DROP TABLE `metadata_table`;


-- Adding indexes
CREATE INDEX idx_image_id ON `image_table`(`id`);
CREATE INDEX idx_file_path ON `image_table`(`file_path`);


-- Adding indexes
CREATE INDEX idx_image_metadata_metadata_id ON `image_metadata`(`metadata_id`);
CREATE INDEX idx_image_metadata_image_id ON `image_metadata`(`image_id`);
CREATE INDEX idx_music_metadata_metadata_id ON `music_metadata`(`metadata_id`);
CREATE INDEX idx_music_metadata_music_id ON `music_metadata`(`music_id`);


-- Adding a new table for tag and metadata relationships with weights

CREATE TABLE `tag_metadata_relationship` (
  `user_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  `metadata_id` INT NOT NULL,
  `weight` FLOAT NOT NULL,
  PRIMARY KEY (`user_id`, `tag_id`, `metadata_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`tag_id`) REFERENCES `tag`(`id`),
  FOREIGN KEY (`metadata_id`) REFERENCES `metadata`(`id`)
);
