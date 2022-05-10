--Connect to host_agent database
\c host_agent;

--Create tables if not exist
CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
  id SERIAL NOT NULL, 
  hostname VARCHAR NOT NULL, 
  cpu_number int NOT NULL, 
  cpu_architecture VARCHAR NOT NULL, 
  cpu_model VARCHAR NOT NULL, 
  cpu_mhz NUMBER NOT NULL, 
  L2_cache int NOT NULL, 
  total_mem int NOT NULL, 
  timestamp timestamp NOT NULL, 
  PRIMARY KEY (id), 
  UNIQUE (hostname)
);

/*INSERT INTO host_info 
VALUES 
  (
    1, 'spry-framework-236416.internal', 
    1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30 GHz', 
    2300.000, 256, 601324, '2019-05-29 17:49:53'
  );
*/

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
  "timestamp" TIMESTAMP NOT NULL, 
  host_id SERIAL NOT NULL, 
  memory_free INT NOT NULL, 
  cpu_idle INT NOT NULL, 
  cpu_kernel INT NOT NULL, 
  disk_io INT NOT NULL, 
  disk_available INT NOT NULL, 
  CONSTRAINT fk_hostid 
  FOREIGN KEY(host_id) REFERENCES host_info(id)
);

/*INSERT INTO host_usage 
VALUES 
  (
    '2019-05-29 16:53:28', 1, 256, 95, 
    0, 0, 31220
  );
*/
