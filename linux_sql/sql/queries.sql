--1.Group hosts by hardware info
SELECT
  cpu_number,
  id,
  total_mem
FROM
  host_info
ORDER BY
  cpu_number,
  id,
  total_mem;

--2.Average memory usage
-- round current ts every 5 mins
SELECT
  date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min'
FROM
  host_usage;

-- you can also create a function for convenience purposes so your query looks cleaner
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

--get hostid,hostname, timestamp in an interval of 5 min,
--avg_used_mem_percentage = average of (total memory - memory free) / total memory * 100

SELECT
  a.host_id,
  b.hostname,
  round5(a.timestamp),
  AVG(b.total_mem - a.memory_free)/ b.total_mem * 100 as avg_used_mem_percentage
FROM
  host_usage a
  INNER JOIN host_info b ON b.id = a.host_id
GROUP BY
  a.host_id,
  b.hostname,
  round5(a.timestamp),
  b.total_mem;

--3.Detect host failure
-- If there are less than 3 data points in 1 interval, print it out
SELECT
  host_id,
  round5(timestamp) as five_min_interval,
  COUNT(timestamp) as num_data_points
FROM
  host_usage
GROUP BY
  round5(timestamp),
  host_id
HAVING
  COUNT(timestamp) < 5;
