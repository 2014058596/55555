local num = tonumber(redis.call('ZSCORE', KEYS[1], ARGV[1]));
if num == nil then
	num = 0;
end
if tonumber(ARGV[2]) > num then
	redis.call('ZADD', KEYS[1], tonumber(ARGV[2]), ARGV[1]);
	redis.call('SET', KEYS[2], ARGV[3]);
	return 1;
end
return 0;