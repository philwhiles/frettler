#/usr/bin/env bash
_frettler_completions()
{
    local instr_opts
    instr_opts="tunings menu lookup patterns fifths custom guitar bassguitar ukelele mandolin banjo"
    local view_opts
    view_opts="horizontal vertical chord progression find display box tab find"
    local note_opts
    note_opts="${noteOpts}"
    local pattern_opts
    pattern_opts="${patternOpts}"
    local tuning_opts
    tuning_opts="${tuningOpts}"
<#noparse>
    local curr_word=${COMP_WORDS[COMP_CWORD]}
    local prev_word=${COMP_WORDS[COMP_CWORD-1]}

    compopt +o default

    case ${prev_word} in
      -a|--added)
        COMPREPLY=( $( compgen -W "${note_opts}" -- "${curr_word}" ) )
        return $?
        ;;
      -n|--notes)
        COMPREPLY=( $( compgen -W "${notes}" -- "${curr_word}" ) )
        return $?
        ;;
      -d|--digits)
        return
        ;;
      -s|--strings)
        COMPREPLY=( $( compgen -W "${note_opts}" -- "${curr_word}" ) )
        return $?
        ;;
      -f|--frets)
        return
        ;;
      -g|--group)
        return
        ;;
      -b|--box)
        return
        ;;
      -p|--progression)
        return
        ;;
      -t|--tuning)
        COMPREPLY=( $( compgen -W "${tuning_opts}" -- "${curr_word}" ) )
        return 0
        ;;
    esac

    case $COMP_CWORD in
        1)
            COMPREPLY=( $(compgen -W "${instr_opts}" -- "${COMP_WORDS[COMP_CWORD]}") )
            ;;
        2)
            if [ ${COMP_WORDS[1]} == "chord" ]; then
                COMPREPLY=( $(compgen -W "${note_opts}" -- "${COMP_WORDS[COMP_CWORD]}") )
              elif [ ${COMP_WORDS[1]} == "patterns" ]; then
                return 0
              elif [ ${COMP_WORDS[1]} == "fifths" ]; then
                return 0
              elif [ ${COMP_WORDS[1]} == "lookup" ]; then
                return 0
              else
                COMPREPLY=( $(compgen -W "${view_opts}" -- "${COMP_WORDS[COMP_CWORD]}") )
              fi
            ;;
        3)
            COMPREPLY=( $(compgen -W "${note_opts}" -- "${COMP_WORDS[COMP_CWORD]}") )
            ;;
        4)
            COMPREPLY=( $(compgen -W "${pattern_opts}" -- "${COMP_WORDS[COMP_CWORD]}") )
            ;;
    esac
    return 0
}
complete -F _frettler_completions frettler
</#noparse>
