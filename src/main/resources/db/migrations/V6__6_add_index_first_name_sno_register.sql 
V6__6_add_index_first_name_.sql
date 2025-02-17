create index sno_user_register_first_name_second_name_index
    on sno_user_register (first_name text_pattern_ops, second_name text_pattern_ops);